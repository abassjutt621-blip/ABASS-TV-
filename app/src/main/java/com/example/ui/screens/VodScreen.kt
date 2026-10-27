package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.VodVideo
import com.example.ui.components.LiveVideoPlayer
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

@Composable
fun VodScreen(
    videos: List<VodVideo>,
    bookmarkedIds: Set<String>,
    selectedVideo: VodVideo?,
    onSelectVideo: (VodVideo?) -> Unit,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf("All", "Cinema", "Documentary", "Entertainment", "Sports", "Business")

    val filteredVideos = remember(selectedCategory, videos) {
        if (selectedCategory == null || selectedCategory == "All") {
            videos
        } else {
            videos.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AbassMidnight),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Spotlight Featured Banner
        item {
            FeaturedVodBanner(
                onWatchPremiere = {
                    val premiere = videos.firstOrNull { it.badge == "PREMIERE" } ?: videos.first()
                    onSelectVideo(premiere)
                }
            )
        }

        // Category Filter Chips
        item {
            Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                Text(
                    text = "ON-DEMAND SHOWS & SPECIALS",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = (selectedCategory == null && cat == "All") || selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedCategory = if (cat == "All") null else cat
                            },
                            label = { Text(cat) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AbassCyan,
                                selectedLabelColor = Color.Black,
                                containerColor = AbassCardSurface,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Video Catalog Items
        items(filteredVideos, key = { it.id }) { video ->
            val isBookmarked = bookmarkedIds.contains(video.id)
            VodVideoCard(
                video = video,
                isBookmarked = isBookmarked,
                onClick = { onSelectVideo(video) },
                onToggleBookmark = { onToggleBookmark(video.id) },
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }

    // Video Detail & Playback Dialog
    selectedVideo?.let { video ->
        VodPlaybackDialog(
            video = video,
            isBookmarked = bookmarkedIds.contains(video.id),
            onToggleBookmark = { onToggleBookmark(video.id) },
            onDismiss = { onSelectVideo(null) }
        )
    }
}

@Composable
private fun FeaturedVodBanner(
    onWatchPremiere: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .clickable(onClick = onWatchPremiere)
            .testTag("featured_vod_banner"),
        shape = RoundedCornerShape(16.dp),
        color = AbassCardSurface
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_cinema_banner_1793121492865),
                contentDescription = "Cinema Premiere Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            // Content Overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AbassGold
                ) {
                    Text(
                        text = "WORLD PREMIERE EXCLUSIVE",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Echoes of the Soil",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Stream the acclaimed masterpiece on ABASS Cinema",
                            color = AbassTextSecondary,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = AbassCyan,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Film",
                                tint = Color.Black,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VodVideoCard(
    video: VodVideo,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("vod_card_${video.id}"),
        shape = RoundedCornerShape(14.dp),
        color = AbassCardSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
    ) {
        Column {
            // Thumbnail with badge and duration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                val thumbRes = video.imageResId ?: R.drawable.img_hero_live_1793121474898
                Image(
                    painter = painterResource(id = thumbRes),
                    contentDescription = video.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Play icon overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.65f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    if (video.badge != null) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (video.badge == "PREMIERE") AbassGold else AbassCyan
                        ) {
                            Text(
                                text = video.badge,
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Black.copy(alpha = 0.8f)
                    ) {
                        Text(
                            text = video.duration,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Video Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = video.title,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${video.category} • ${video.views} • ${video.date}",
                        color = AbassTextSecondary,
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) AbassCyan else AbassTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VodPlaybackDialog(
    video: VodVideo,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AbassDarkBlue,
        titleContentColor = Color.White,
        textContentColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = video.category.uppercase(),
                    color = AbassCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
        },
        text = {
            Column {
                // Video Player Container for VOD
                LiveVideoPlayer(
                    videoUrl = video.videoUrl,
                    channelName = "ABASS On-Demand",
                    currentShowTitle = video.title,
                    viewerCount = 1204,
                    isPlaying = true,
                    isMuted = false,
                    isAudioOnly = false,
                    streamQuality = "1080p FHD",
                    isFullscreen = false,
                    onTogglePlay = {},
                    onToggleMute = {},
                    onToggleFullscreen = {},
                    onSelectQuality = {},
                    onCheerReaction = {},
                    floatingReactionsCount = 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = video.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${video.views} • Released ${video.date} • Duration ${video.duration}",
                    color = AbassGold,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = video.description,
                    color = AbassTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onToggleBookmark,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBookmarked) AbassCardElevated else AbassCyan
                    )
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmarked) AbassCyan else Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isBookmarked) "Saved" else "Watch Later",
                        color = if (isBookmarked) AbassCyan else Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}
