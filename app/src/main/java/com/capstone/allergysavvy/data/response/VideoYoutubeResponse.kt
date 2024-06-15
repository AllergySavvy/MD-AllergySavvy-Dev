package com.capstone.allergysavvy.data.response

data class SearchListResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: VideoId,
    val snippet: Snippet
)

data class VideoId(
    val videoId: String
)

data class Snippet(
    val title: String,
    val thumbnails: Thumbnails,
    val channelTitle: String
)

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail?,
    val high: Thumbnail?,
    val standard: Thumbnail?,
    val maxres: Thumbnail?
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

