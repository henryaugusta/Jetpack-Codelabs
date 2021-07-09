package com.feylabs.firrieflix.ui.favorite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.databinding.ItemFavoriteBinding
import com.feylabs.firrieflix.databinding.ItemMovieBinding
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.like.LikeButton
import com.like.OnLikeListener

class FavoritePagedListAdapter(private val mContext: Context) :
    PagedListAdapter<FavoriteEntity, FavoritePagedListAdapter.FavViewHolder>(DIFF_CALLBACK) {

    private lateinit var myInterface : FavoriteInterface

    fun setInterface(newInterface: FavoriteInterface){
        this.myInterface=newInterface
    }

    inner class FavViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FavoriteEntity) {
            with(binding) {

                labelMovieTitle.text = model.name
                labelMovieDesc.text = model.desc

                myCard.setOnClickListener {
                    myInterface.onClick(model)
                }

                btnDelete.setOnClickListener {
                    myInterface.onDelete(model)
                }

                Glide.with(this.root)
                    .load(ApiConfig.IMAGE_BASE_PATH + model.poster)
                    .centerCrop()
                    .placeholder(R.drawable.dark_placeholder)
                    .thumbnail(Glide.with(this.root).load(R.raw.loading))
                    .dontAnimate()
                    .into(binding.imgCover)


            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> =
            object : DiffUtil.ItemCallback<FavoriteEntity>() {
                override fun areItemsTheSame(old: FavoriteEntity, new: FavoriteEntity): Boolean {
                    return old.movId == new.movId
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    old: FavoriteEntity,
                    new: FavoriteEntity
                ): Boolean {
                    return old == new
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritePagedListAdapter.FavViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position) as FavoriteEntity)
    }

    interface FavoriteInterface{
        fun onClick(model:FavoriteEntity)
        fun onDelete(model:FavoriteEntity)
    }
}