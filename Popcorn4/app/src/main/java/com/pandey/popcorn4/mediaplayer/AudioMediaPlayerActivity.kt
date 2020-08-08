package com.pandey.popcorn4.mediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseActivity
import com.pandey.popcorn4.mediaplayer.MediaPlayerService.LocalBinder
import kotlinx.android.synthetic.main.activity_media_player.*


class AudioMediaPlayerActivity : BaseActivity() {


    private lateinit var mediaPlayerService: MediaPlayerService

    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocalBinder
            mediaPlayerService = binder.service
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        media_play_btn.setOnClickListener {
            playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg")
        }

        media_pause_btn.setOnClickListener {
//            stopService(Intent(this, MediaPlayerService::class.java))
        }
    }

    private fun playAudio(media: String) {
        if (!isServiceBound) {
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            startService(playerIntent)
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("ServiceState", isServiceBound)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        isServiceBound = savedInstanceState?.getBoolean("ServiceState")!!
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            mediaPlayerService.stopSelf()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_media_player
    }

}