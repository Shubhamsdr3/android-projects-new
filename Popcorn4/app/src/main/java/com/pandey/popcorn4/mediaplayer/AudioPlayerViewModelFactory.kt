package com.pandey.popcorn4.mediaplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AudioPlayerViewModelFactory(private val audioFilePath: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(String::class.java)) {
            return AudioPlayerViewModel(audioFilePath) as T
        }
        throw IllegalArgumentException("Unknown view Model class")
    }
}