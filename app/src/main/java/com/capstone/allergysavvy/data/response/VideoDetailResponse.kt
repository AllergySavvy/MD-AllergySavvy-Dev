package com.capstone.allergysavvy.data.response

data class VideoDetailsResponse(
    val items: List<VideoDetailItem>
)

data class VideoDetailItem(
    val id: String,
    val snippet: Snippet,
    val statistics: Statistics
)

data class Statistics(
    val viewCount: String
)

data class Video(
    val title: String,
    val videoId: String,
    val thumbnailUrl: String,
    val channelTitle: String,
    val viewCount: String
)
