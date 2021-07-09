package com.feylabs.firrieflix.view.detail

import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.adapter.ArtistAdapter
import com.feylabs.firrieflix.databinding.ActivityDetailMovieBinding
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.view.movie.MovieFragment.Companion.MOVIE_ID
import com.feylabs.firrieflix.viewmodel.DetailMovieViewModel


class DetailMovieActivity : AppCompatActivity() {

    val vbind by lazy { ActivityDetailMovieBinding.inflate(layoutInflater) }

    lateinit var movie: MovieModel

    lateinit var artistAdapter: ArtistAdapter

    val detailMovieViewModel by lazy { ViewModelProvider(this).get(DetailMovieViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)

        //Scroll to Top of Screen
        vbind.myScrollView.fullScroll(ScrollView.FOCUS_UP)
        vbind.myScrollView.smoothScrollTo(0,0)

        val movieIDFromIntent = intent.getIntExtra(MOVIE_ID, 0)
        if (movieIDFromIntent != 0) {
            movie = detailMovieViewModel.findMovieById(movieIDFromIntent)
        }

        vbind.labelBack.setOnClickListener {
            super.onBackPressed()
        }

        setUpArtistAdapter()
        setUpRecyclerView()
        setUpLayout()
    }

    private fun setUpLayout() {
        vbind.apply {

            btnShare.setOnClickListener {
                val shareText = ShareCompat.IntentBuilder.from(this@DetailMovieActivity)
                    .setType("text/plain")
                    .setText("""
                        #Firrieflix
                        Watch this cool ${movie.title} Film at Firriflix,
                        directed by ${movie.director} at ${movie.releaseDate}
                        short story line : 
                        ${movie.description}
                    """.trimIndent())
                    .intent
                if (shareText.resolveActivity(this@DetailMovieActivity.packageManager) != null) {
                    startActivity(shareText)
                }
            }

            labelMovieTitle.text = movie.title
            labelMovieDirector.text = movie.director
            labelMovieDate.text = movie.releaseDate
            labelStoryboard.text = movie.description



            if (movie.type==MovieModel.MovieType.MOVIE){
                Glide.with(applicationContext)
                    .load(movie.img_link.toInt())
                    .into(vbind.imgCover)
            }else{
                Glide.with(applicationContext)
                    .load(movie.img_link)
                    .into(vbind.imgCover)
            }



            when(movie.imbdRating.toInt()){
                in 0..20->{
                    labelMovieRate.text="⭐"
                }
                in 21..40->{
                    labelMovieRate.text="⭐⭐"
                }
                in 41..70->{
                    labelMovieRate.text="⭐⭐⭐"
                }
                in 71..80->{
                    labelMovieRate.text="⭐⭐⭐⭐"
                }
                in 81..100->{
                    labelMovieRate.text="⭐⭐⭐⭐⭐"
                }
            }

        }

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
        artistAdapter.setData(movie.artist)
        artistAdapter.notifyDataSetChanged()
    }
}