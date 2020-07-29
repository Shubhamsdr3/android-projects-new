package com.pandey.popcorn4.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.PowerManager
import com.pandey.popcorn4.R

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, R.raw.beleiver)
        mediaPlayer?.isLooping = true
        mediaPlayer?.setWakeMode(application.applicationContext,  PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }
}