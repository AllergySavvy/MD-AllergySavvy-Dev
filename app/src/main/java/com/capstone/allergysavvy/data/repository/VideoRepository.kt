package com.capstone.allergysavvy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.capstone.allergysavvy.data.paging.VideoPagingSource
import com.capstone.allergysavvy.data.retrofit.ApiService

class VideoRepository private constructor(
    private val apiService: ApiService
) {
    fun getVideoYoutube(q: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { VideoPagingSource(apiService, q) }
    ).liveData

    companion object {
        @Volatile
        private var INSTANCE: VideoRepository? = null

        fun getInstance(apiService: ApiService): VideoRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VideoRepository(apiService).also { INSTANCE = it }
            }
    }
}
