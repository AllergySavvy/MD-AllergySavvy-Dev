package com.capstone.allergysavvy.ui.main.fragment.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.data.repository.VideoRepository

class VideoViewModelFactory(private val repository: VideoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel class not found " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: VideoViewModelFactory? = null

        fun getInstance(repository: VideoRepository): VideoViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VideoViewModelFactory(
                    repository
                )
            }.also { INSTANCE = it }
    }

}