package com.pandey.popcorn4.mediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pandey.popcorn4.base.NewBaseFragment
import com.pandey.popcorn4.databinding.FragmentAudioPlayerBinding

class AudioPlayerFragment : NewBaseFragment<FragmentAudioPlayerBinding>() {

    private var isServiceBound = false

    private lateinit var mediaPlayerService: MediaPlayerService

    private lateinit var serviceConnectionManager: ServiceConnectionManager

    companion object {

        fun newInstance(): AudioPlayerFragment {
            return AudioPlayerFragment()
        }
    }

    override fun initLayout() {
        serviceConnectionManager = ServiceConnectionManager()
    }

    override fun initListener() {
        viewBinding?.audioPlayerView?.setOnClickListener {
            playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg")
        }

        //        media_pause_btn.setOnClickListener {
////            stopService(Intent(this, MediaPlayerService::class.java))
//        }
    }

    private fun playAudio(media: String) {
        if (!isServiceBound) {
            val playerIntent = Intent(activity, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            context?.startService(playerIntent)
            context?.bindService(playerIntent, serviceConnectionManager, Context.BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    override fun getBindingView(): FragmentAudioPlayerBinding {
        return FragmentAudioPlayerBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            context?.unbindService(serviceConnectionManager)
            mediaPlayerService.stopSelf()
        }
    }

    inner class ServiceConnectionManager: ServiceConnection  {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MediaPlayerService.LocalBinder
            mediaPlayerService = binder.service
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }
}