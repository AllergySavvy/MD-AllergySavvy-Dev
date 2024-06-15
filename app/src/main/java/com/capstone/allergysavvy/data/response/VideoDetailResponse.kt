package com.capstone.allergysavvy.data.response

data class VideoDetailsResponse(
    val items: List<VideoDetailItem>
)

data class VideoDetailItem(
    val id: String,
    val snippet: Snippet,
    val statistics: Statistics,
    val contentDetails: ContentDetails?
)


data class ContentDetails(
    val duration: String
)

data class Statistics(
    val viewCount: String
)

data class Video(
    val title: String,
    val id: String,
    val thumbnailUrl: String,
    val channelTitle: String,
    val viewCount: String,
    val duration: String
)

