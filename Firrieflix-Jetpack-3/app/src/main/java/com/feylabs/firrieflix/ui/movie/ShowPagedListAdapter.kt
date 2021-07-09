package com.feylabs.firrieflix.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.databinding.ItemMovieBinding
import com.feylabs.firrieflix.util.networking.ApiConfig

class ShowPagedListAdapter :
    PagedListAdapter<ShowLocalEntity, ShowPagedListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var myInterface: ShowInterface

    fun setInterface(newInterface: ShowInterface) {
        this.myInterface = newInterface
    }

    inner class MyViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ShowLocalEntity) {
            binding.apply {

                root.setOnClickListener {
                    myInterface.onclick(model)
                }

                labelMovieTitle.text = model.name
                labelMovieDesc.text = model.overview
                Glide.with(root)
                    .load(model.original_language?.let { ApiConfig.setFlagImage(it) })
                    .into(imageFlag)
                labelMovieDate.text = model.first_air_date

                btnShare.setOnClickListener {
                    myInterface.share(model)
                }

                root.context.apply {
                    model.vote_average?.let {
                        when (it) {
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
                    }


                    Glide
                        .with(this)
                        .load(ApiConfig.IMAGE_BASE_PATH + model.poster_path)
                        .centerCrop()
                        .placeholder(R.drawable.dark_placeholder)
                        .thumbnail(Glide.with(this).load(R.raw.loading))
                        .dontAnimate()
                        .into(binding.imgCover)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ShowLocalEntity> =
            object : DiffUtil.ItemCallback<ShowLocalEntity>() {
                override fun areItemsTheSame(
                    old: ShowLocalEntity,
                    new: ShowLocalEntity
                ): Boolean {
                    return old == new
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    old: ShowLocalEntity,
                    new: ShowLocalEntity
                ): Boolean {
                    return old == new
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowPagedListAdapter.MyViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position) as ShowLocalEntity)
    }

    interface ShowInterface {
        fun onclick(model:ShowLocalEntity)
        fun share(model: ShowLocalEntity)
    }
}