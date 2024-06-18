package com.capstone.allergysavvy.data.response

data class SearchListResponse(
    val items: List<VideoItem>,
    val nextPageToken: String?
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
    val channelTitle: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail?,
    val high: Thumbnail?,
    val maxres: Thumbnail?
)

data class Thumbnail(
    val url: String
)

