package com.az.channel.adapter

import android.graphics.Color
import android.os.Build
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.az.channel.model.ChannelItemNew
import android.view.View.OnFocusChangeListener
import androidx.cardview.widget.CardView
import com.az.channel.R
import kotlinx.android.synthetic.main.activity_main.*

var channelListArray: List<ChannelItemNew> = emptyList()

class ChannelListNewAdapter(private val movies: List<ChannelItemNew>): RecyclerView.Adapter<MoviesViewHolder>() {

    var onItemClick: ((ChannelItemNew) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.az.channel.R.layout.channel_list_item, parent, false)
        val ac_view = LayoutInflater.from(parent.context).inflate(com.az.channel.R.layout.activity_main, parent, false)
        val recycler_view: RecyclerView = ac_view.findViewById(R.id.recyclerView)




        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            val lm: RecyclerView.LayoutManager = recyclerView.getLayoutManager()

            // Return false if scrolled to the bounds and allow focus to move off the list
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    return@OnKeyListener recycler_view.findViewHolderForAdapterPosition(1)?.itemView?.performClick() == true
                } else  {
                    return@OnKeyListener tryMoveSelection(recycler_view)
                }
            }
            false
        })





        channelListArray = movies
        return MoviesViewHolder(view, onItemClick)
    }

    private fun tryMoveSelection(recycler_view: RecyclerView): Boolean {
        return recycler_view.findViewHolderForAdapterPosition(1)?.itemView?.performClick() == true
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])
    }
}

class MoviesViewHolder(itemView: View, onItemClick: ((ChannelItemNew) -> Unit)?): RecyclerView.ViewHolder(itemView) {
    private val photo: ImageView = itemView.findViewById(com.az.channel.R.id.movie_photo)
    private val title: TextView = itemView.findViewById(com.az.channel.R.id.movie_title)
    private val cardView: CardView = itemView.findViewById(com.az.channel.R.id.spdv_details_cv)

    init {
        itemView.setOnClickListener {
            onItemClick?.invoke(channelListArray[adapterPosition])
        }



        itemView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                title.setTextColor(Color.BLACK)
                cardView.cardElevation = 10F
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    cardView.outlineSpotShadowColor = Color.parseColor("#FF0D45")
                }
            } else {
                title.setTextColor(Color.WHITE)
                cardView.cardElevation = 0F
            }
        }
    }

    fun bind(channel: ChannelItemNew) {
        Glide.with(itemView.context).load(channel.logoUrl).into(photo)
        title.text = channel.name
    }

}
