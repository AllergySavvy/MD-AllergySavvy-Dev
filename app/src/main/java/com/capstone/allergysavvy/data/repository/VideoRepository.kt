package com.capstone.allergysavvy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.allergysavvy.BuildConfig
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.data.response.SearchListResponse
import com.capstone.allergysavvy.data.response.Thumbnails
import com.capstone.allergysavvy.data.response.Video
import com.capstone.allergysavvy.data.response.VideoDetailsResponse
import com.capstone.allergysavvy.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepository private constructor(
    private val apiService: ApiService
) {
    fun getVideoYoutube(q: String): LiveData<Result<List<Video>>> {
        val result = MutableLiveData<Result<List<Video>>>()
        result.value = Result.Loading

        apiService.searchVideos(
            "snippet",
            q,
            "video",
            BuildConfig.API_KEY_YOUTUBE,
            25
        ).enqueue(object : Callback<SearchListResponse> {
            override fun onResponse(
                call: Call<SearchListResponse>,
                response: Response<SearchListResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val videoIds = response.body()!!.items.joinToString(",") { it.id.videoId }
                    fetchVideoDetails(videoIds, result)
                } else {
                    Log.e("VideoRepository", "Response failed: ${response.message()}")
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<SearchListResponse>, t: Throwable) {
                Log.e("VideoRepository", "Network request failed: ${t.message}")
                result.value = Result.Error(t.message ?: "Error get video")
            }
        })

        return result
    }

    private fun fetchVideoDetails(videoIds: String, result: MutableLiveData<Result<List<Video>>>) {
        apiService.getVideoDetails(
            "snippet,statistics,contentDetails",
            videoIds,
            BuildConfig.API_KEY_YOUTUBE
        ).enqueue(object : Callback<VideoDetailsResponse> {
            override fun onResponse(
                call: Call<VideoDetailsResponse>,
                response: Response<VideoDetailsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val videoYoutube = response.body()!!.items.map { result ->
                        val thumbnails = result.snippet.thumbnails
                        val thumbnailUrl = getMaxResThumbnailUrl(thumbnails)
                        val duration = result.contentDetails?.duration ?: "PT0M0S"
                        Video(
                            result.snippet.title,
                            result.id,
                            thumbnailUrl,
                            result.snippet.channelTitle,
                            result.statistics.viewCount,
                            duration
                        )
                    }
                    result.value = Result.Success(videoYoutube)
                } else {
                    Log.e("VideoRepository", "Response failed: ${response.message()}")
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<VideoDetailsResponse>, t: Throwable) {
                Log.e("VideoRepository", "Network request failed: ${t.message}")
                result.value = Result.Error(t.message ?: "Error get video details")
            }
        })
    }


    private fun getMaxResThumbnailUrl(thumbnails: Thumbnails): String {
        return when {
            thumbnails.maxres != null -> thumbnails.maxres.url
            thumbnails.high != null -> thumbnails.high.url
            thumbnails.medium != null -> thumbnails.medium.url
            else -> thumbnails.default.url
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: VideoRepository? = null

        fun getInstance(apiService: ApiService): VideoRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VideoRepository(apiService).also { INSTANCE = it }
            }
    }
}
