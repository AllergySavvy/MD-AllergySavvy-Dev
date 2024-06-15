package com.capstone.allergysavvy.ui.main.fragment.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.repository.VideoRepository
import com.capstone.allergysavvy.data.response.Video
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _videos = MutableLiveData<Result<List<Video>>>()
    val videos: LiveData<Result<List<Video>>> get() = _videos

    fun searchVideo(query: String) {
        viewModelScope.launch {
            _videos.postValue(Result.Loading)
            val result = repository.getVideoYoutube(query)
            result.observeForever {
                _videos.postValue(it)
            }
        }
    }
}
