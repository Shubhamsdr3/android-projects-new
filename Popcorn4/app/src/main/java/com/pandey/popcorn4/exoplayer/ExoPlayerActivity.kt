package com.pandey.popcorn4.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandey.popcorn4.R
import com.pandey.popcorn4.exoplayer.data.MediaResource
import kotlinx.android.synthetic.main.activity_exo_player.*


class ExoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        val mediaObjects =  MediaResource.MEDIA_OBJECTS.toList()
        recycler_view.setMediaObjects(mediaObjects)
        val adapter = VideoPlayerAdapter(mediaObjects)
        recycler_view.adapter = adapter
    }


    override fun onDestroy() {
        if (recycler_view != null) {
            recycler_view.releasePlayer()
        }
        super.onDestroy()
    }
}
