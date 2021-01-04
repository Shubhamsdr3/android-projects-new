package com.pandey.popcorn4.mediaplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pandey.popcorn4.databinding.ActivityMediaPlayerBinding


class AudioMediaPlayerActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMediaPlayerBinding

    private var isServiceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        startFragment(viewBinding.audioFragmentContainer.id, AudioPlayerFragment.newInstance())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("ServiceState", isServiceBound)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        isServiceBound = savedInstanceState?.getBoolean("ServiceState")!!
    }
}