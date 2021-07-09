package com.feylabs.firrieflix.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.data.source.remote.responses.MovieCreditsResponse
import com.feylabs.firrieflix.databinding.ItemArtistBinding

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var list = mutableListOf<MovieCreditsResponse.Cast>()

    fun setData(artist : MutableList<MovieCreditsResponse.Cast>){
        list.clear()
        this.list.addAll(artist)
        notifyDataSetChanged()
    }

    inner class ArtistViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val vbind = ItemArtistBinding.bind(v)

        fun bind(model: MovieCreditsResponse.Cast) {
            vbind.cardArtist.setOnClickListener {
                Toast.makeText(vbind.root.context,model.name,Toast.LENGTH_LONG).show()
            }
            vbind.artistName.text=model.original_name
            vbind.artistRole.text=model.character
            Glide
                .with(vbind.root)
                .load(ApiConfig.IMAGE_BASE_PATH + model.profile_path)
                .centerCrop()
                .placeholder(R.drawable.dark_placeholder)
                .thumbnail(Glide.with(vbind.root).load(R.raw.loading))
                .dontAnimate() //so there's no weird crossfade
                .into(vbind.imgArtist)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(v)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}