package com.capstone.allergysavvy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.allergysavvy.BuildConfig
import com.capstone.allergysavvy.data.response.*
import com.capstone.allergysavvy.data.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException

class VideoPagingSource(
    private val apiService: ApiService,
    private val q: String
) : PagingSource<String, Video>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Video> {
        val position = params.key ?: ""

        return try {
            val response = apiService.searchVideos(
                part = "snippet",
                query = q,
                type = "video",
                apiKey = BuildConfig.API_KEY_YOUTUBE,
                maxResults = params.loadSize,
                pageToken = position
            )

            val videoIds = response.items.joinToString(",") { it.id.videoId }
            val videoDetailsResponse = apiService.getVideoDetails(
                part = "snippet,statistics,contentDetails",
                id = videoIds,
                apiKey = BuildConfig.API_KEY_YOUTUBE
            )

            val videos = videoDetailsResponse.items.map { detailItem ->
                val snippet = detailItem.snippet
                val thumbnails = snippet.thumbnails
                val thumbnailUrl = getMaxResThumbnailUrl(thumbnails)
                val duration = detailItem.contentDetails?.duration ?: "PT0M0S"
                Video(
                    title = snippet.title,
                    id = detailItem.id,
                    thumbnailUrl = thumbnailUrl,
                    channelTitle = snippet.channelTitle,
                    viewCount = detailItem.statistics.viewCount,
                    duration = duration
                )
            }

            LoadResult.Page(
                data = videos,
                prevKey = null,
                nextKey = response.nextPageToken
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Video>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    private fun getMaxResThumbnailUrl(thumbnails: Thumbnails): String {
        return when {
            thumbnails.maxres != null -> thumbnails.maxres.url
            thumbnails.high != null -> thumbnails.high.url
            thumbnails.medium != null -> thumbnails.medium.url
            else -> thumbnails.default.url
        }
    }
}
