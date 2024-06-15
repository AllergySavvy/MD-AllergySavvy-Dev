package com.capstone.allergysavvy.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatViewsVideo(viewsCount: String): String {
    val count = viewsCount.toLongOrNull() ?: return viewsCount
    return when {
        count >= 1_000_000 -> String.format("%.1fM Views", count / 1_000_000.0)
        count >= 1_000 -> String.format("%.1fK Views", count / 1_000.0)
        else -> "$count Views"
    }
}
