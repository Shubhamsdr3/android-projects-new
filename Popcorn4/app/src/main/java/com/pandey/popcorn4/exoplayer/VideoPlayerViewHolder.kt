package com.pandey.popcorn4.exoplayer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pandey.popcorn4.exoplayer.data.MediaObject
import kotlinx.android.synthetic.main.vedio_list_item.view.*

class VideoPlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(mediaObject: MediaObject) {
        itemView.tag = this
        itemView.title.text = mediaObject.title
        Glide.with(itemView.context).load(mediaObject.thumbnail).into(itemView.thumbnail)
    }
}