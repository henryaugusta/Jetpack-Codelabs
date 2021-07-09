package com.feylabs.firrieflix.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.databinding.ItemArtistBinding
import com.feylabs.firrieflix.model.MovieModel

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var list = mutableListOf<MovieModel.Artist>()

    fun setData(artist : MutableList<MovieModel.Artist>){
        list.clear()
        this.list.addAll(artist)
        notifyDataSetChanged()
    }

    inner class ArtistViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val vbind = ItemArtistBinding.bind(v)

        fun bind(model: MovieModel.Artist) {
            vbind.cardArtist.setOnClickListener {
                Toast.makeText(vbind.root.context,model.name,Toast.LENGTH_LONG).show()
            }
            vbind.artistName.text=model.name
            vbind.artistRole.text=model.play_as
            Glide.with(vbind.root)
                .load(model.img_link)
                .centerCrop()
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