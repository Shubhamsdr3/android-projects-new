package com.pandey.popcorn4.mediaplayer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import timber.log.Timber
import java.io.IOException

class MediaPlayerService : Service() , MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {

    private var mediaPlayer: MediaPlayer? = null

    private val iBinder: IBinder = LocalBinder()

    private lateinit var audioManager: AudioManager

    private var resumePosition = 0

    private lateinit var mediaFile: String

    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            mediaFile = intent?.extras?.getString("media")!!
        } catch (e: NullPointerException) {
            stopSelf()
        }
        if (!requestFocus()) {
            stopSelf()
        }
        initMediaPlayer()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setOnErrorListener(this)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnBufferingUpdateListener(this)
        mediaPlayer?.setOnSeekCompleteListener(this)
        mediaPlayer?.setOnInfoListener(this)

        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer?.reset()
//        mediaPlayer?.setAudioAttributes(AudioAttributes)
        try {
            mediaPlayer!!.setDataSource(mediaFile)
        } catch (e: IOException) {
            e.printStackTrace()
            stopSelf()
        }
        mediaPlayer!!.prepareAsync()
    }

    private fun playMedia() {
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }
    }

    private fun stopMedia() {
        if (mediaPlayer == null) return
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
    }

    private fun pauseMedia() {
        if (mediaPlayer == null) return
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            resumePosition = mediaPlayer?.currentPosition!!
        }
    }

    private fun resumeMedia() {
        if (mediaPlayer == null) return
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.seekTo(resumePosition)
            mediaPlayer!!.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            stopMedia()
            mediaPlayer!!.release()
        }
        removeAudioFocus()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stopMedia()
        stopSelf()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        playMedia()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when(what) {
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK -> {
                Timber.d("MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK %s", extra)
            }

            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {
                Timber.d("MEDIA ERROR SERVER DIED %s", extra)
            }

            MediaPlayer.MEDIA_ERROR_UNKNOWN ->  {
                Timber.d("MEDIA ERROR UNKNOWN %s", extra)
            }
        }
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer?) {

    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {

    }

    override fun onAudioFocusChange(focusChange: Int) {
        when(focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                if (mediaPlayer == null) {
                    initMediaPlayer()
                }
                else if (mediaPlayer != null && !mediaPlayer!!.isPlaying){
                    mediaPlayer!!.start()
                    mediaPlayer!!.setVolume(1.0f, 1.0f)
                }
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    mediaPlayer?.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying){
                    mediaPlayer?.setVolume(0.1f, 0.1f)
                }
            }
        }
    }

    private fun requestFocus() : Boolean {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    private fun removeAudioFocus(): Boolean {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this)
    }

    class LocalBinder : Binder() {

        val service: MediaPlayerService get() = MediaPlayerService()
    }
}