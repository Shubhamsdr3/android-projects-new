package com.pandey.popcorn4.mediaplayer

import android.content.Intent
import android.os.Bundle
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseActivity
import kotlinx.android.synthetic.main.activity_media_player.*

class AudioMediaPlayerActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        media_play_btn.setOnClickListener {
            startService(Intent(this, MediaPlayerService::class.java))
        }

        media_pause_btn.setOnClickListener {
            stopService(Intent(this, MediaPlayerService::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_media_player
    }

}