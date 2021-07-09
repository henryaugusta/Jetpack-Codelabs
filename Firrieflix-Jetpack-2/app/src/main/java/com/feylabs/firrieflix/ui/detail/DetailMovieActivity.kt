package com.feylabs.firrieflix.ui.detail

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
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.data.source.remote.responses.MovieDetailResponse
import com.feylabs.firrieflix.data.source.remote.responses.ShowDetailResponse
import com.feylabs.firrieflix.databinding.ActivityDetailMovieBinding
import com.feylabs.firrieflix.util.BaseActivity
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.viewmodel.ViewModelFactory

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DetailMovieActivity : BaseActivity(), KodeinAware {

    override val kodein by kodein()

    val vbind by lazy { ActivityDetailMovieBinding.inflate(layoutInflater) }

    val firrieflixFactory: ViewModelFactory by instance()

    lateinit var movie: MovieDetailResponse
    lateinit var show: ShowDetailResponse

    lateinit var artistAdapter: ArtistAdapter

    lateinit var detailMovieViewModel: DetailMovieViewModel

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val TYPE = "TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)

        setupView()

        detailMovieViewModel =
            ViewModelProvider(this, firrieflixFactory).get(DetailMovieViewModel::class.java)


        setUpArtistAdapter()
        setUpRecyclerView()

        val movieID = intent.getStringExtra(MOVIE_ID).toString()
        Log.d("checkMovieID", movieID)

        if (intent.getStringExtra(TYPE) == DetailMovieViewModel.MovType.MOVIE.toString()) {
            getString(R.string.mov).showLongToast()

            detailMovieViewModel.getCredits(movieID, DetailMovieViewModel.MovType.MOVIE)
            detailMovieViewModel.getDetailMovie(movieID)
            initDetailMovieObserver(movieID)
        } else {
            detailMovieViewModel.getCredits(movieID, DetailMovieViewModel.MovType.SHOW)
            detailMovieViewModel.getDetailShow(movieID)
            "Show".showLongToast()
            initDetailShowObserver(movieID)
        }


        initMovieCreditsObserver()
    }

    private fun setupView() {
        actionBar?.hide()
        supportActionBar?.hide()

        showLoadingScreen()

        //Scroll to Top of Screen
        vbind.myScrollView.fullScroll(ScrollView.FOCUS_UP)
        vbind.myScrollView.smoothScrollTo(0, 0)

    }

    private fun initMovieCreditsObserver() {
        detailMovieViewModel.movieCreditRes.observe(this, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                    it.data?.let { it1 -> artistAdapter.setData(it1.toMutableList()) }
                    Log.d("movie_detail", "success")
                }
                is ResponseHelper.Error -> {
                    hideLoadingScreen(it.message.toString())
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
                                    labelMovieRate.text = "⭐"
                                }
                                in 2.1..4.0 -> {
                                    labelMovieRate.text = "⭐⭐"
                                }
                                in 4.1..7.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐"
                                }
                                in 7.1..8.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐⭐"
                                }
                                in 8.1..10.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐⭐⭐"
                                }
                            }
                        }

                    }
                    Log.d("movie_detail", "success")
                }
                is ResponseHelper.Error -> {
                    hideLoadingScreen(it.message.toString())
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

    private fun initDetailMovieObserver(movieID: String) {

        detailMovieViewModel.getDetailMovie(movieID).observe(this, Observer {
            when (it) {
                is ResponseHelper.Success -> {
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

                            when (vote_average) {
                                in 0.0..2.0 -> {
                                    labelMovieRate.text = "⭐"
                                }
                                in 2.1..4.0 -> {
                                    labelMovieRate.text = "⭐⭐"
                                }
                                in 4.1..7.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐"
                                }
                                in 7.1..8.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐⭐"
                                }
                                in 8.1..10.0 -> {
                                    labelMovieRate.text = "⭐⭐⭐⭐⭐"
                                }
                            }
                        }

                    }
                    Log.d("movie_detail", "success")
                }
                is ResponseHelper.Error -> {
                    Log.d("movie_detail", "error get film -> ${it.message.toString()}")
                }
                is ResponseHelper.Loading -> {
                    Log.d("movie_detail", "loading film")
                }
                else -> {
                }
            }

        })
    }

    private fun setUpRecyclerView() {
        vbind.rvArtist.apply {
            adapter = artistAdapter
            layoutManager =
                LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun setUpArtistAdapter() {
        artistAdapter = ArtistAdapter()
        artistAdapter.notifyDataSetChanged()
    }

    fun hideLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewGone()
            labelLoading.text = message
        }
    }

    fun showLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewVisible()
            labelLoading.text = message
        }
    }
}