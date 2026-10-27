package com.example.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AbassBorder
import com.example.ui.theme.AbassCardElevated
import com.example.ui.theme.AbassCardSurface
import com.example.ui.theme.AbassCyan
import com.example.ui.theme.AbassDarkBlue
import com.example.ui.theme.AbassGold
import com.example.ui.theme.AbassLiveRed
import com.example.ui.theme.AbassMidnight
import com.example.ui.theme.AbassTextMuted
import com.example.ui.theme.AbassTextSecondary
import com.example.viewmodel.AbassTvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AbassTvViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var searchDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.userNotificationMessage) {
        uiState.userNotificationMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearNotification()
        }
    }

    val navItems = listOf(
        NavigationItem("Live TV", Icons.Default.LiveTv, "nav_live"),
        NavigationItem("Guide", Icons.Default.CalendarMonth, "nav_guide"),
        NavigationItem("On Demand", Icons.Default.VideoLibrary, "nav_vod"),
        NavigationItem("News", Icons.Default.Newspaper, "nav_news"),
        NavigationItem("Studio", Icons.Default.SatelliteAlt, "nav_studio")
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AbassMidnight,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .testTag("app_top_bar"),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AbassDarkBlue,
                    titleContentColor = Color.White
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_abass_logo_1793121431672),
                            contentDescription = "ABASS TV Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "ABASS TV",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp,
                                    color = Color.White
                                )
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AbassLiveRed)
                                )
                            }
                            Text(
                                text = uiState.selectedChannel.name,
                                fontSize = 10.sp,
                                color = AbassCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                actions = {
                    if (uiState.isAudioOnly) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AbassCardElevated,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = "Audio Mode",
                                    tint = AbassCyan,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "Audio Saver",
                                    color = AbassCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { searchDialogOpen = true },
                        modifier = Modifier.testTag("search_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search shows and channels",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AbassDarkBlue,
                contentColor = Color.White
            ) {
                navItems.forEach { item ->
                    val isSelected = uiState.activeTab.startsWith(item.label.take(4))
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setActiveTab(item.label) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = AbassCyan,
                            indicatorColor = AbassCyan,
                            unselectedIconColor = AbassTextSecondary,
                            unselectedTextColor = AbassTextSecondary
                        ),
                        modifier = Modifier.testTag(item.tag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = uiState.activeTab,
            label = "tab_fade",
            modifier = Modifier.padding(innerPadding)
        ) { tab ->
            when {
                tab.startsWith("Live", ignoreCase = true) -> {
                    LiveTvScreen(
                        uiState = uiState,
                        onSelectChannel = { viewModel.selectChannel(it) },
                        onTogglePlay = { viewModel.togglePlayPause() },
                        onToggleMute = { viewModel.toggleMute() },
                        onToggleFullscreen = { viewModel.toggleFullscreen() },
                        onSelectQuality = { viewModel.setQuality(it) },
                        onCheerReaction = { viewModel.triggerCheerReaction() },
                        onSendMessage = { text, sender, city -> viewModel.sendChatMessage(text, sender, city) },
                        onAlertClick = { viewModel.selectNewsAlert(it) },
                        onToggleReminder = { viewModel.toggleProgramReminder(it) }
                    )
                }
                tab.startsWith("Guide", ignoreCase = true) -> {
                    GuideScreen(
                        programs = uiState.programs,
                        channels = uiState.channels,
                        reminderProgramIds = uiState.reminderProgramIds,
                        onToggleReminder = { viewModel.toggleProgramReminder(it) },
                        onWatchChannel = {
                            viewModel.selectChannel(it)
                            viewModel.setActiveTab("Live TV")
                        }
                    )
                }
                tab.startsWith("On Demand", ignoreCase = true) -> {
                    VodScreen(
                        videos = uiState.vodVideos,
                        bookmarkedIds = uiState.bookmarkedVideoIds,
                        selectedVideo = uiState.selectedVodVideo,
                        onSelectVideo = { viewModel.selectVodVideo(it) },
                        onToggleBookmark = { viewModel.toggleBookmark(it) }
                    )
                }
                tab.startsWith("News", ignoreCase = true) -> {
                    NewsScreen(
                        newsAlerts = uiState.newsAlerts,
                        selectedAlert = uiState.selectedNewsAlert,
                        onSelectAlert = { viewModel.selectNewsAlert(it) }
                    )
                }
                tab.startsWith("Studio", ignoreCase = true) -> {
                    StudioScreen(
                        isAudioOnly = uiState.isAudioOnly,
                        onToggleAudioOnly = { viewModel.toggleAudioOnly() },
                        onSubmitTip = { viewModel.submitNewsTip(it) },
                        tipStatusMessage = uiState.tipSubmissionStatus,
                        onClearTipStatus = { viewModel.clearTipSubmissionStatus() }
                    )
                }
                else -> {
                    LiveTvScreen(
                        uiState = uiState,
                        onSelectChannel = { viewModel.selectChannel(it) },
                        onTogglePlay = { viewModel.togglePlayPause() },
                        onToggleMute = { viewModel.toggleMute() },
                        onToggleFullscreen = { viewModel.toggleFullscreen() },
                        onSelectQuality = { viewModel.setQuality(it) },
                        onCheerReaction = { viewModel.triggerCheerReaction() },
                        onSendMessage = { text, sender, city -> viewModel.sendChatMessage(text, sender, city) },
                        onAlertClick = { viewModel.selectNewsAlert(it) },
                        onToggleReminder = { viewModel.toggleProgramReminder(it) }
                    )
                }
            }
        }
    }

    if (searchDialogOpen) {
        SearchBroadcastDialog(
            viewModel = viewModel,
            onDismiss = { searchDialogOpen = false }
        )
    }
}

data class NavigationItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val tag: String
)

@Composable
fun SearchBroadcastDialog(
    viewModel: AbassTvViewModel,
    onDismiss: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    val matchedChannels = remember(query) {
        if (query.isBlank()) emptyList()
        else uiState.channels.filter {
            it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
        }
    }

    val matchedVideos = remember(query) {
        if (query.isBlank()) emptyList()
        else uiState.vodVideos.filter {
            it.title.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
        }
    }

    val matchedPrograms = remember(query) {
        if (query.isBlank()) emptyList()
        else uiState.programs.filter {
            it.title.contains(query, ignoreCase = true) || it.genre.contains(query, ignoreCase = true)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AbassDarkBlue,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Search ABASS TV", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search shows, channels, movies...", color = AbassTextMuted, fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_query_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AbassCardElevated,
                        unfocusedContainerColor = AbassCardElevated,
                        focusedBorderColor = AbassCyan,
                        unfocusedBorderColor = AbassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (query.isNotBlank() && matchedChannels.isEmpty() && matchedVideos.isEmpty() && matchedPrograms.isEmpty()) {
                        item {
                            Text(
                                text = "No broadcasts found matching '$query'",
                                color = AbassTextMuted,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    if (matchedChannels.isNotEmpty()) {
                        item {
                            Text(text = "Channels", color = AbassCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        items(matchedChannels) { ch ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectChannel(ch)
                                        viewModel.setActiveTab("Live TV")
                                        onDismiss()
                                    },
                                shape = RoundedCornerShape(8.dp),
                                color = AbassCardSurface
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = ch.name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "• ${ch.category}", color = AbassGold, fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    if (matchedVideos.isNotEmpty()) {
                        item {
                            Text(text = "On Demand", color = AbassCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        items(matchedVideos) { vid ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectVodVideo(vid)
                                        viewModel.setActiveTab("On Demand")
                                        onDismiss()
                                    },
                                shape = RoundedCornerShape(8.dp),
                                color = AbassCardSurface
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = vid.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "${vid.category} • ${vid.duration}", color = AbassTextSecondary, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}
