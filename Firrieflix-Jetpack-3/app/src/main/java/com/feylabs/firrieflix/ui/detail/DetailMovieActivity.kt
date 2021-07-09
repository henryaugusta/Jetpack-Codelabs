package com.feylabs.firrieflix.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ScrollView
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.data.source.remote.responses.MovieDetailResponse
import com.feylabs.firrieflix.data.source.remote.responses.ShowDetailResponse
import com.feylabs.firrieflix.databinding.ActivityDetailMovieBinding
import com.feylabs.firrieflix.util.BaseActivity
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.viewmodel.ViewModelFactory
import com.like.LikeButton
import com.like.OnLikeListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DetailMovieActivity : BaseActivity(), KodeinAware {

    override val kodein by kodein()

    val vbind by lazy { ActivityDetailMovieBinding.inflate(layoutInflater) }
//    val firrieflixFactory: ViewModelFactory by instance()

    lateinit var movie: MovieDetailResponse
    lateinit var show: ShowDetailResponse


    lateinit var detailMovieViewModel: DetailMovieViewModel

    var isMovie = false

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val TYPE = "TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)

        setupView()

        val factory = ViewModelFactory(
            FirrieflixRepository(
                RemoteDataSource(),
                LocalDataSource(FavoriteDatabase(this).favDao())
            )
        )

        detailMovieViewModel =
            ViewModelProvider(this, factory).get(DetailMovieViewModel::class.java)

        val movieID = intent.getStringExtra(MOVIE_ID).toString()
        Log.d("checkMovieID", movieID)


        detailMovieViewModel.checkIfLiked(movieID).observe(this, Observer {
            vbind.like.isLiked = it.isNotEmpty()
        })

        vbind.includeLoading.srl.apply {
            setOnRefreshListener {
                this.isRefreshing = false
                fetchData(movieID)
            }
        }

        fetchData(movieID)


        vbind.like.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                if (isMovie) {
                    "Added to Favorite".showLongToast()
                    movie.apply {
                        detailMovieViewModel.addToFav(
                            FavoriteEntity(
                                movId = movie.id,
                                name = title,
                                desc = overview,
                                imgPreview = backdrop_path,
                                favType = 1,
                                poster = poster_path
                            )
                        )
                    }
                    showSweetAlert(
                        getString(R.string.success),
                        getString(R.string.success_insert) + " ${movie.title} " + getString(R.string.to_favorite),
                        R.color.alert_green
                    )
                } else {
                    show.apply {
                        detailMovieViewModel.addToFav(
                            FavoriteEntity(
                                movId = show.id,
                                name = original_name,
                                desc = overview,
                                imgPreview = backdrop_path,
                                favType = 2,
                                poster = poster_path
                            )
                        )
                    }
                    showSweetAlert(
                        getString(R.string.success),
                        getString(R.string.success_insert) + show.name + getString(R.string.to_favorite),
                        R.color.alert_green
                    )
                }
            }

            override fun unLiked(likeButton: LikeButton?) {
                detailMovieViewModel.deleteFavorite(movieID)
                getString(R.string.deleted_from_fav).showLongToast()
                if (isMovie) {
                    showSweetAlert(
                        getString(R.string.success),
                        getString(R.string.success_delete) + " ${movie.title} " + getString(R.string.from_fav),
                        R.color.alert_green
                    )
                }else{
                    showSweetAlert(
                        getString(R.string.success),
                        getString(R.string.success_delete) + " ${show.original_name} " + getString(R.string.from_fav),
                        R.color.alert_green
                    )
                }

            }

        })


    }

    private fun fetchData(movieID: String) {
        if (intent.getStringExtra(TYPE) == DetailMovieViewModel.MovType.MOVIE.toString()) {
            isMovie = true
            getString(R.string.mov).showLongToast()
            detailMovieViewModel.getDetailMovie(movieID)
            initDetailMovieObserver(movieID)
        } else {
            detailMovieViewModel.getDetailShow(movieID)
            getString(R.string.shows).showLongToast()
            initDetailShowObserver(movieID)
        }
    }

    private fun setupView() {
        actionBar?.hide()
        supportActionBar?.hide()

        showLoadingScreen()

        //Scroll to Top of Screen
        vbind.myScrollView.fullScroll(ScrollView.FOCUS_UP)
        vbind.myScrollView.smoothScrollTo(0, 0)

    }


    @SuppressLint("SetTextI18n")
    private fun initDetailShowObserver(movieID: String) {
        detailMovieViewModel.getDetailShow(movieID).observe(this, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                    show = it.data!!
                    vbind.apply {
                        btnShare.setOnClickListener {
                            val shareText = ShareCompat.IntentBuilder.from(this@DetailMovieActivity)
                                .setType("text/plain")
                                .setText(
                                    """
                        #Firrieflix
                        Watch this cool ${show.name} Film at Firriflix,
                        episode count  :  ${show.number_of_episodes} 
                        released at :  ${show.first_air_date} \n
                        short story line : 
                        ${movie.overview}
                    """.trimIndent()
                                )
                                .intent
                            if (shareText.resolveActivity(this@DetailMovieActivity.packageManager) != null) {
                                startActivity(shareText)
                            }
                        }

                        show.apply {
                            labelMovieTitle.text = show.name
                            labelMovieDate.text = show.first_air_date
                            labelStoryboard.text = show.overview
                            var genreText = "Genre : "
                            var prodHouseText = ""

                            val genre = this.genres
                            val prodHouse = this.production_companies

                            genre.forEachIndexed { index, element ->
                                genreText = "$genreText\n${index + 1}. ${element.name}"
                            }

                            prodHouse.forEachIndexed { index, element ->
                                prodHouseText = "$prodHouseText ${index + 1}. ${element.name}"
                            }

                            labelMovieGenre.text = genreText
                            labelProductionHouse.text = prodHouseText
                            labelLanguage.text = getString(R.string.lang) + " -$original_language"
                            loadGlide(ApiConfig.setFlagImage(original_language), imageFlag, false)
                            loadGlide(
                                ApiConfig.IMAGE_POSTER_BASE_PATH + backdrop_path,
                                imgBackdrop,
                                false
                            )
                            loadGlide(
                                ApiConfig.IMAGE_POSTER_BASE_PATH + poster_path,
                                imgCover,
                                true
                            )


                            Glide
                                .with(vbind.root)
                                .load(ApiConfig.IMAGE_BASE_PATH + poster_path)
                                .centerCrop()
                                .placeholder(R.drawable.dark_placeholder)
                                .thumbnail(Glide.with(root).load(R.raw.loading))
                                .dontAnimate() //so there's no weird crossfade
                                .into(vbind.imgCover)

                            when (vote_average) {
                                in 0.0..2.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)  + getString(R.string.star1)
                                }
                                in 2.1..4.0 -> {
                                    labelMovieRate.text =  getString(R.string.reting_)  +getString(R.string.star2)
                                }
                                in 4.1..7.0 -> {
                                    labelMovieRate.text =  getString(R.string.reting_)  +getString(R.string.start3)
                                }
                                in 7.1..8.0 -> {
                                    labelMovieRate.text =  getString(R.string.reting_)  +getString(R.string.star4)
                                }
                                in 8.1..10.0 -> {
                                    labelMovieRate.text =  getString(R.string.reting_)  +getString(R.string.star5)
                                }
                            }
                        }

                    }
                    Log.d("movie_detail", "success")
                }
                is ResponseHelper.Error -> {
                    showLoadingScreen(getString(R.string.you_need_inet_detail))
                    getString(R.string.error_message_toast).showLongToast()
                    Log.d("movie_detail", "error get film -> ${it.message.toString()}")
                }
                is ResponseHelper.Loading -> {
                    showLoadingScreen()
                    Log.d("movie_detail", "loading film")
                }
                else -> {
                    // Do Nothing, because all response wrapped in ResponseHelper, so this line will not reached
                }
            }
        })
    }

    private fun loadGlide(url: String, target: ImageView, isCropped: Boolean) {
        val glider = Glide.with(vbind.root).load(url)
        if (isCropped) {
            glider.centerCrop()
        }
        glider
            .dontAnimate() //so there's no weird crossfade
            .placeholder(R.drawable.dark_placeholder)
            .thumbnail(Glide.with(vbind.root).load(R.raw.loading))
            .dontAnimate() //so there's no weird crossfade
            .into(target)
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailMovieObserver(movieID: String) {
        detailMovieViewModel.getDetailMovie(movieID).observe(this, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                    movie = it.data!!
                    vbind.apply {
                        btnShare.setOnClickListener {
                            val shareText = ShareCompat.IntentBuilder.from(this@DetailMovieActivity)
                                .setType("text/plain")
                                .setText(
                                    """
                        #Firrieflix
                        Watch this cool ${movie.title} Film at Firriflix,
                        duration  :  ${movie.runtime} 
                        released at :  ${movie.release_date} \n
                        short story line : 
                        ${movie.overview}
                    """.trimIndent()
                                )
                                .intent
                            if (shareText.resolveActivity(this@DetailMovieActivity.packageManager) != null) {
                                startActivity(shareText)
                            }
                        }

                        movie.apply {
                            labelMovieTitle.text = movie.title
                            labelMovieDate.text = movie.release_date
                            labelStoryboard.text = movie.overview
                            var genreText = "Genre : "
                            var prodHouseText = ""

                            val genre = this.genres
                            val prodHouse = this.production_companies

                            genre.forEachIndexed { index, element ->
                                genreText = "$genreText\n${index + 1}. ${element.name}"
                            }

                            prodHouse.forEachIndexed { index, element ->
                                prodHouseText = "$prodHouseText ${index + 1}. ${element.name}"
                            }

                            labelMovieGenre.text = genreText
                            labelProductionHouse.text = prodHouseText


                            Glide.with(root)
                                .load(ApiConfig.setFlagImage(original_language))
                                .into(imageFlag)

                            Glide
                                .with(root)
                                .load(ApiConfig.IMAGE_POSTER_BASE_PATH + backdrop_path)
                                .placeholder(R.drawable.dark_placeholder)
                                .thumbnail(Glide.with(root).load(R.raw.loading))
                                .dontAnimate() //so there's no weird crossfade
                                .into(imgBackdrop)

                            Glide
                                .with(vbind.root)
                                .load(ApiConfig.IMAGE_BASE_PATH + poster_path)
                                .centerCrop()
                                .placeholder(R.drawable.dark_placeholder)
                                .thumbnail(Glide.with(root).load(R.raw.loading))
                                .dontAnimate() //so there's no weird crossfade
                                .into(vbind.imgCover)

                            labelRealRating.text = "(${vote_average}/10)"

                            when (vote_average) {
                                in 0.0..2.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)+ getString(R.string.star1)
                                }
                                in 2.1..4.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)+getString(R.string.star2)
                                }
                                in 4.1..7.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)+getString(R.string.start3)
                                }
                                in 7.1..8.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)+getString(R.string.star4)
                                }
                                in 8.1..10.0 -> {
                                    labelMovieRate.text = getString(R.string.reting_)+getString(R.string.star5)
                                }
                            }
                        }

                    }
                    Log.d("movie_detail", "success")
                }
                is ResponseHelper.Error -> {
                    showLoadingScreen(getString(R.string.you_need_inet_detail))
                    Log.d("movie_detail", "error get film -> ${it.message.toString()}")
                }
                is ResponseHelper.Loading -> {
                    showLoadingScreen()
                    Log.d("movie_detail", "loading film")
                }
                else -> {
                }
            }

        })
    }


    private fun hideLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            this.root.makeViewGone()
            labelLoading.text = message
        }
    }

    private fun showLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            this.root.makeViewVisible()
            labelLoading.text = message
        }
    }
}