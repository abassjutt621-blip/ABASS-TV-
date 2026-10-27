package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Channel
import com.example.data.ChatMessage
import com.example.data.MockTvRepository
import com.example.data.NewsAlert
import com.example.data.NewsTipSubmission
import com.example.data.Program
import com.example.data.VodVideo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TvUiState(
    val channels: List<Channel> = MockTvRepository.channels,
    val selectedChannel: Channel = MockTvRepository.channels.first(),
    val programs: List<Program> = MockTvRepository.programs,
    val vodVideos: List<VodVideo> = MockTvRepository.vodVideos,
    val chatMessages: List<ChatMessage> = MockTvRepository.initialChatMessages,
    val newsAlerts: List<NewsAlert> = MockTvRepository.newsAlerts,
    val activeTab: String = "Live TV",
    val isPlaying: Boolean = true,
    val isMuted: Boolean = false,
    val streamQuality: String = "1080p FHD",
    val isAudioOnly: Boolean = false,
    val isFullscreen: Boolean = false,
    val selectedVodVideo: VodVideo? = null,
    val selectedNewsAlert: NewsAlert? = null,
    val bookmarkedVideoIds: Set<String> = setOf("vod_1"),
    val reminderProgramIds: Set<String> = setOf("prog_8"),
    val searchQuery: String = "",
    val totalCheerReactions: Int = 1840,
    val floatingReactionsCount: Int = 0,
    val tipSubmissionStatus: String? = null,
    val userNotificationMessage: String? = null
)

class AbassTvViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TvUiState())
    val uiState: StateFlow<TvUiState> = _uiState.asStateFlow()

    init {
        // Background simulated viewer heartbeat and periodic chat
        startLiveSimulation()
    }

    private fun startLiveSimulation() {
        viewModelScope.launch {
            val cities = listOf("Abuja", "Lagos", "Accra", "Dakar", "London", "Nairobi", "Cairo", "Atlanta")
            val sampleNames = listOf("Suleiman T.", "Kofi B.", "Aisha G.", "Emmanuel O.", "Maryam K.", "Babatunde R.")
            val sampleComments = listOf(
                "Superb clarity on ABASS TV! Proud viewer! 🌟",
                "Watching with the whole family tonight.",
                "The analysis on African trade was spot on! 👏",
                "Loving the soundtrack on this program.",
                "Greetings from the diaspora! Keeping connected to home."
            )
            var commentIndex = 0

            while (true) {
                delay(12000)
                val newViewerDelta = (-15..25).random()
                _uiState.update { current ->
                    val updatedViewerCount = (current.selectedChannel.viewerCount + newViewerDelta).coerceAtLeast(1000)
                    val updatedChannel = current.selectedChannel.copy(viewerCount = updatedViewerCount)
                    
                    val newMsg = ChatMessage(
                        id = "sim_${System.currentTimeMillis()}",
                        sender = sampleNames[commentIndex % sampleNames.size],
                        city = cities.random(),
                        message = sampleComments[commentIndex % sampleComments.size],
                        timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
                        reactionsCount = (2..8).random()
                    )
                    commentIndex++

                    current.copy(
                        selectedChannel = updatedChannel,
                        chatMessages = (current.chatMessages + newMsg).takeLast(25),
                        totalCheerReactions = current.totalCheerReactions + (1..3).random()
                    )
                }
            }
        }
    }

    fun selectChannel(channel: Channel) {
        _uiState.update {
            it.copy(
                selectedChannel = channel,
                selectedVodVideo = null,
                isPlaying = true
            )
        }
    }

    fun setActiveTab(tab: String) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun togglePlayPause() {
        _uiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun toggleMute() {
        _uiState.update { it.copy(isMuted = !it.isMuted) }
    }

    fun setQuality(quality: String) {
        val isAudio = quality.contains("Audio", ignoreCase = true)
        _uiState.update {
            it.copy(
                streamQuality = quality,
                isAudioOnly = isAudio
            )
        }
    }

    fun toggleAudioOnly() {
        _uiState.update {
            val newAudioState = !it.isAudioOnly
            it.copy(
                isAudioOnly = newAudioState,
                streamQuality = if (newAudioState) "Audio Only" else "1080p FHD"
            )
        }
    }

    fun toggleFullscreen() {
        _uiState.update { it.copy(isFullscreen = !it.isFullscreen) }
    }

    fun sendChatMessage(text: String, senderName: String, city: String) {
        if (text.isBlank()) return
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            sender = if (senderName.isBlank()) "Viewer" else senderName,
            city = if (city.isBlank()) "Live Stream" else city,
            message = text.trim(),
            timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
            reactionsCount = 1
        )
        _uiState.update {
            it.copy(
                chatMessages = it.chatMessages + newMsg,
                totalCheerReactions = it.totalCheerReactions + 1
            )
        }
    }

    fun triggerCheerReaction() {
        _uiState.update {
            it.copy(
                totalCheerReactions = it.totalCheerReactions + 1,
                floatingReactionsCount = it.floatingReactionsCount + 1
            )
        }
    }

    fun toggleBookmark(videoId: String) {
        _uiState.update { current ->
            val set = current.bookmarkedVideoIds.toMutableSet()
            val added = if (set.contains(videoId)) {
                set.remove(videoId)
                false
            } else {
                set.add(videoId)
                true
            }
            current.copy(
                bookmarkedVideoIds = set,
                userNotificationMessage = if (added) "Saved to your Watchlist" else "Removed from Watchlist"
            )
        }
    }

    fun toggleProgramReminder(programId: String) {
        _uiState.update { current ->
            val set = current.reminderProgramIds.toMutableSet()
            val added = if (set.contains(programId)) {
                set.remove(programId)
                false
            } else {
                set.add(programId)
                true
            }
            current.copy(
                reminderProgramIds = set,
                userNotificationMessage = if (added) "Broadcast reminder set! You will be alerted before airtime." else "Reminder removed"
            )
        }
    }

    fun selectVodVideo(video: VodVideo?) {
        _uiState.update {
            it.copy(
                selectedVodVideo = video,
                isPlaying = true
            )
        }
    }

    fun selectNewsAlert(alert: NewsAlert?) {
        _uiState.update { it.copy(selectedNewsAlert = alert) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun submitNewsTip(submission: NewsTipSubmission) {
        _uiState.update {
            it.copy(
                tipSubmissionStatus = "Thank you! Your story tip has been transmitted to the ABASS TV Newsroom desk at abasstv70@gmail.com."
            )
        }
    }

    fun clearNotification() {
        _uiState.update { it.copy(userNotificationMessage = null) }
    }

    fun clearTipSubmissionStatus() {
        _uiState.update { it.copy(tipSubmissionStatus = null) }
    }
}
