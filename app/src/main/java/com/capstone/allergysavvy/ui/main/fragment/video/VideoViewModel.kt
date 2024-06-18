package com.capstone.allergysavvy.ui.main.fragment.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.capstone.allergysavvy.data.repository.VideoRepository

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    fun searchVideo(query: String) = repository.getVideoYoutube(query).cachedIn(viewModelScope)
}
