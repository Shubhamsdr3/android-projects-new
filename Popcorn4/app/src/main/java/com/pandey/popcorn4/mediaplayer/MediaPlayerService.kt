package com.pandey.popcorn4.mediaplayer

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.pandey.popcorn4.R
import kotlinx.android.synthetic.main.audion_player_view.view.*
import timber.log.Timber
import java.io.IOException

class MediaPlayerService : Service() {

    private val iBinder: IBinder = LocalBinder()

    private lateinit var audioPlayerManager: AudioPlayerManager

    companion object {
        private const val CHANNEL_ID = "popcorn"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        audioPlayerManager = AudioPlayerManager()
        try {
            val mediaFile = intent?.extras?.getString("media")!!
            audioPlayerManager.initMediaPlayer(mediaFile)
        } catch (e: NullPointerException) {
            stopSelf()
        }
//        if (!requestFocus()) {
//            stopSelf()
//        }
        val input : String = intent?.extras?.getString("input") ?: ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification(input)
        }
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(contentText: String) {
        createNotificationChannel()
        val notificationIntent = Intent(this, AudioMediaPlayerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val remoteView = RemoteViews(packageName, R.layout.audion_player_view)
        val notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing music")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_popcorn)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteView)
                .build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    "popocorn music",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
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

    class LocalBinder : Binder() {

        val service: MediaPlayerService get() = MediaPlayerService()
    }
}