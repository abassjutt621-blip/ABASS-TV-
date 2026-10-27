package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Channel
import com.example.data.NewsAlert
import com.example.data.Program
import com.example.ui.components.BreakingNewsBanner
import com.example.ui.components.ChannelSelectorRow
import com.example.ui.components.LiveChatSection
import com.example.ui.components.LiveVideoPlayer
import com.example.ui.theme.AbassBorder
import com.example.ui.theme.AbassCardElevated
import com.example.ui.theme.AbassCardSurface
import com.example.ui.theme.AbassCyan
import com.example.ui.theme.AbassGold
import com.example.ui.theme.AbassLiveRed
import com.example.ui.theme.AbassMidnight
import com.example.ui.theme.AbassTextMuted
import com.example.ui.theme.AbassTextSecondary
import com.example.viewmodel.TvUiState

@Composable
fun LiveTvScreen(
    uiState: TvUiState,
    onSelectChannel: (Channel) -> Unit,
    onTogglePlay: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleFullscreen: () -> Unit,
    onSelectQuality: (String) -> Unit,
    onCheerReaction: () -> Unit,
    onSendMessage: (text: String, sender: String, city: String) -> Unit,
    onAlertClick: (NewsAlert) -> Unit,
    onToggleReminder: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AbassMidnight)
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        // Live Video Broadcast Player
        LiveVideoPlayer(
            videoUrl = uiState.selectedChannel.streamUrl,
            channelName = uiState.selectedChannel.name,
            currentShowTitle = uiState.selectedChannel.currentShow,
            viewerCount = uiState.selectedChannel.viewerCount,
            isPlaying = uiState.isPlaying,
            isMuted = uiState.isMuted,
            isAudioOnly = uiState.isAudioOnly,
            streamQuality = uiState.streamQuality,
            isFullscreen = uiState.isFullscreen,
            onTogglePlay = onTogglePlay,
            onToggleMute = onToggleMute,
            onToggleFullscreen = onToggleFullscreen,
            onSelectQuality = onSelectQuality,
            onCheerReaction = onCheerReaction,
            floatingReactionsCount = uiState.floatingReactionsCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .aspectRatio(16f / 9f)
        )

        // Breaking News Ticker (Top News Alert)
        if (uiState.newsAlerts.isNotEmpty()) {
            BreakingNewsBanner(
                alert = uiState.newsAlerts.first(),
                onAlertClick = onAlertClick,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Channels Selector Carousel
        ChannelSelectorRow(
            channels = uiState.channels,
            selectedChannelId = uiState.selectedChannel.id,
            onSelectChannel = onSelectChannel
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Current Program Info & Schedule Bar
        CurrentShowCard(
            channel = uiState.selectedChannel,
            programs = uiState.programs,
            reminderProgramIds = uiState.reminderProgramIds,
            onToggleReminder = onToggleReminder,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Live Chat & Community Section
        LiveChatSection(
            messages = uiState.chatMessages,
            totalReactions = uiState.totalCheerReactions,
            onSendMessage = onSendMessage,
            onCheer = onCheerReaction,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
private fun CurrentShowCard(
    channel: Channel,
    programs: List<Program>,
    reminderProgramIds: Set<String>,
    onToggleReminder: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentProg = programs.firstOrNull { it.channelId == channel.id && it.isLiveNow }
        ?: programs.firstOrNull { it.channelId == channel.id }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AbassCardSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(AbassLiveRed)
                    )
                    Text(
                        text = "NOW BROADCASTING",
                        color = AbassLiveRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Text(
                    text = channel.currentShowTime,
                    color = AbassGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = currentProg?.title ?: channel.currentShow,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currentProg?.description ?: channel.tagline,
                color = AbassTextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Show Progress Bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Host: ${currentProg?.host ?: "ABASS Studio"}",
                        color = AbassCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Genre: ${currentProg?.genre ?: channel.category}",
                        color = AbassTextMuted,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { currentProg?.progressPercent ?: 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AbassCyan,
                    trackColor = AbassBorder
                )
            }

            if (currentProg != null) {
                Spacer(modifier = Modifier.height(12.dp))
                val isReminderSet = reminderProgramIds.contains(currentProg.id)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = { onToggleReminder(currentProg.id) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isReminderSet) AbassCyan else Color.White
                        ),
                        modifier = Modifier.testTag("toggle_reminder_${currentProg.id}")
                    ) {
                        Icon(
                            imageVector = if (isReminderSet) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                            contentDescription = "Reminder",
                            modifier = Modifier.size(16.dp),
                            tint = if (isReminderSet) AbassCyan else Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isReminderSet) "Reminder Active" else "Notify Next Airing",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
