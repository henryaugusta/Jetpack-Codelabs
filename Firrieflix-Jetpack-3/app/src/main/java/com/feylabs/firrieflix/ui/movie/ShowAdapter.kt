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
import com.feylabs.firrieflix.data.source.remote.responses.ShowResponse
import com.feylabs.firrieflix.databinding.ItemMovieBinding


class ShowAdapter : RecyclerView.Adapter<ShowAdapter.MovieViewHolder>() {

    val listMovie = mutableListOf<ShowResponse.Result>()

    lateinit var movieInterface: MovieInterface

    fun setData(setterMovie: MutableList<ShowResponse.Result>) {
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
        fun bind(model: ShowResponse.Result) {

            vbind.root.setOnClickListener {
                movieInterface.onclick(model)
            }

            vbind.apply {
                labelMovieTitle.text = model.name
                labelMovieDesc.text = model.overview
                Glide.with(root)
                    .load(ApiConfig.setFlagImage(model.original_language))
                    .into(imageFlag)
                labelMovieDate.text = model.first_air_date

                btnShare.setOnClickListener {
                    movieInterface.share(model)
                }


                when (model.vote_average) {
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

            Glide
                .with(vbind.root)
                .load(IMAGE_BASE_PATH + model.poster_path)
                .centerCrop()
                .into(vbind.imgCover)
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
        fun onclick(model: ShowResponse.Result)
        fun share(model: ShowResponse.Result)
    }
}