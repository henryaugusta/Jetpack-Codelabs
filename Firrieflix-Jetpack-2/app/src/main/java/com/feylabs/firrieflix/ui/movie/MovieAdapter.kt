package com.feylabs.firrieflix.ui.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.util.networking.ApiConfig.Companion.IMAGE_BASE_PATH
import com.feylabs.firrieflix.data.source.remote.responses.MovieResponse
import com.feylabs.firrieflix.databinding.ItemMovieBinding


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val listMovie = mutableListOf<MovieResponse.Result>()

    lateinit var movieInterface: MovieInterface

    fun setData(setterMovie: MutableList<MovieResponse.Result>) {
        listMovie.clear()
        listMovie.addAll(setterMovie)
        notifyDataSetChanged()
    }

    fun setMyMovieInterface(interfaceMovie: MovieInterface) {
        this.movieInterface = interfaceMovie
    }


    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vbind = ItemMovieBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(model: MovieResponse.Result) {

            vbind.apply {

                root.setOnClickListener {
                    movieInterface.onclick(model)
                }

                labelMovieTitle.text = model.title
                labelMovieDesc.text = model.overview
                Glide.with(root)
                    .load(ApiConfig.setFlagImage(model.original_language))
                    .into(imageFlag)
                labelMovieDate.text = model.release_date

                btnShare.setOnClickListener {
                    movieInterface.share(model)
                }

                vbind.root.context.apply {
                    when (model.vote_average) {
                        in 0.0..2.0 -> {
                            labelMovieRate.text = getString(R.string.star1)
                        }
                        in 2.1..4.0 -> {
                            labelMovieRate.text = getString(R.string.star2)
                        }
                        in 4.1..7.0 -> {
                            labelMovieRate.text = getString(R.string.start3)
                        }
                        in 7.1..8.0 -> {
                            labelMovieRate.text = getString(R.string.star4)
                        }
                        in 8.1..10.0 -> {
                            labelMovieRate.text = getString(R.string.star5)
                        }
                    }

                    Glide
                        .with(this)
                        .load(IMAGE_BASE_PATH + model.poster_path)
                        .centerCrop()
                        .placeholder(R.drawable.dark_placeholder)
                        .thumbnail(Glide.with(this).load(R.raw.loading))
                        .dontAnimate()
                        .into(vbind.imgCover)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    interface MovieInterface {
        fun onclick(model: MovieResponse.Result)
        fun share(model: MovieResponse.Result)
    }
}