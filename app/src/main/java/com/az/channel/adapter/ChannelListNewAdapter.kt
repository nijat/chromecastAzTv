package com.az.channel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.az.channel.R
import com.az.channel.model.ChannelItemNew

var channelListArray: List<ChannelItemNew> = emptyList()


class ChannelListNewAdapter(private val movies: List<ChannelItemNew>): RecyclerView.Adapter<MoviesViewHolder>() {
    var onItemClick: ((ChannelItemNew) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.channel_list_item, parent, false)
        channelListArray = movies
        return MoviesViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])
    }
}


class MoviesViewHolder(itemView: View, onItemClick: ((ChannelItemNew) -> Unit)?): RecyclerView.ViewHolder(itemView) {
    private val photo: ImageView = itemView.findViewById(R.id.movie_photo)
    private val title: TextView = itemView.findViewById(R.id.movie_title)

    init {
        itemView.setOnClickListener {
            onItemClick?.invoke(channelListArray[adapterPosition])
        }
    }

    fun bind(channel: ChannelItemNew) {
        Glide.with(itemView.context).load(channel.logoUrl).into(photo)
        title.text = channel.name
    }

}
