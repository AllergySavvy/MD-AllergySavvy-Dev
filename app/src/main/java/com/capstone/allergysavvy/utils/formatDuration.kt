package com.capstone.allergysavvy.utils

import android.annotation.SuppressLint
import java.util.regex.Pattern

@SuppressLint("DefaultLocale")
fun formatDuration(duration: String): String {
    val pattern = Pattern.compile("PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?")
    val matcher = pattern.matcher(duration)

    if (!matcher.matches()) {
        return "0:00"
    }

    val hours = matcher.group(1)?.toIntOrNull() ?: 0
    val minutes = matcher.group(2)?.toIntOrNull() ?: 0
    val seconds = matcher.group(3)?.toIntOrNull() ?: 0

    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%d:%02d", minutes, seconds)
    }
}