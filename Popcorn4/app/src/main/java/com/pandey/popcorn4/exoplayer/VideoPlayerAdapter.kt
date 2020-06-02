package com.pandey.popcorn4.exoplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.popcorn4.R
import com.pandey.popcorn4.exoplayer.data.MediaObject

class VideoPlayerAdapter(private val videoList: List<MediaObject>) : RecyclerView.Adapter<VideoPlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayerViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.vedio_list_item, parent, false)
        return VideoPlayerViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoPlayerViewHolder, position: Int) {
        holder.bind(videoList[position])
    }
}