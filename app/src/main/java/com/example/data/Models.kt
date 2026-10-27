package com.example.data

data class Channel(
    val id: String,
    val name: String,
    val tagline: String,
    val category: String,
    val streamUrl: String,
    val currentShow: String,
    val currentShowTime: String,
    val viewerCount: Int,
    val isLive: Boolean = true,
    val imageResId: Int? = null,
    val qualityList: List<String> = listOf("1080p FHD", "720p HD", "480p SD", "Audio Only")
)

data class Program(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Int,
    val progressPercent: Float = 0f,
    val day: String = "Today",
    val genre: String,
    val host: String = "ABASS Studio",
    val isLiveNow: Boolean = false,
    val hasCatchUp: Boolean = false,
    val isReminderSet: Boolean = false
)

data class VodVideo(
    val id: String,
    val title: String,
    val category: String,
    val duration: String,
    val views: String,
    val date: String,
    val videoUrl: String,
    val imageResId: Int? = null,
    val description: String,
    val badge: String? = null,
    val isBookmarked: Boolean = false
)

data class ChatMessage(
    val id: String,
    val sender: String,
    val city: String,
    val message: String,
    val timestamp: String,
    val isModerator: Boolean = false,
    val reactionsCount: Int = 0
)

data class NewsAlert(
    val id: String,
    val headline: String,
    val fullStory: String,
    val category: String,
    val timeAgo: String,
    val isBreaking: Boolean = true
)

data class SatelliteInfo(
    val satelliteName: String,
    val orbitalPosition: String,
    val frequency: String,
    val polarization: String,
    val symbolRate: String,
    val fec: String,
    val modulation: String,
    val coverage: String
)

data class NewsTipSubmission(
    val fullName: String,
    val email: String,
    val phone: String,
    val location: String,
    val topic: String,
    val storyDetails: String
)
