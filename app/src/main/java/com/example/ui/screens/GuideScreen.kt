package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Channel
import com.example.data.Program
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
fun GuideScreen(
    programs: List<Program>,
    channels: List<Channel>,
    reminderProgramIds: Set<String>,
    onToggleReminder: (String) -> Unit,
    onWatchChannel: (Channel) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDayIndex by remember { mutableIntStateOf(0) }
    var selectedChannelId by remember { mutableStateOf<String?>(null) }
    val days = listOf("Today", "Tomorrow", "Weekly Guide")

    val filteredPrograms = remember(selectedDayIndex, selectedChannelId, programs) {
        val targetDay = when (selectedDayIndex) {
            0 -> "Today"
            1 -> "Tomorrow"
            else -> null
        }
        programs.filter { prog ->
            (targetDay == null || prog.day == targetDay) &&
                    (selectedChannelId == null || prog.channelId == selectedChannelId)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AbassMidnight)
            .padding(top = 8.dp)
    ) {
        // Day Tabs
        TabRow(
            selectedTabIndex = selectedDayIndex,
            containerColor = AbassDarkBlue,
            contentColor = AbassCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedDayIndex]),
                    color = AbassCyan,
                    height = 3.dp
                )
            }
        ) {
            days.forEachIndexed { index, day ->
                Tab(
                    selected = selectedDayIndex == index,
                    onClick = { selectedDayIndex = index },
                    text = {
                        Text(
                            text = day,
                            fontSize = 13.sp,
                            fontWeight = if (selectedDayIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedDayIndex == index) AbassCyan else AbassTextSecondary
                        )
                    },
                    modifier = Modifier.testTag("epg_day_tab_$index")
                )
            }
        }

        // Channel Filters Chips Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedChannelId == null,
                    onClick = { selectedChannelId = null },
                    label = { Text("All Channels") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AbassCyan,
                        selectedLabelColor = Color.Black,
                        containerColor = AbassCardSurface,
                        labelColor = Color.White
                    )
                )
            }
            items(channels) { ch ->
                FilterChip(
                    selected = selectedChannelId == ch.id,
                    onClick = {
                        selectedChannelId = if (selectedChannelId == ch.id) null else ch.id
                    },
                    label = { Text(ch.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AbassCyan,
                        selectedLabelColor = Color.Black,
                        containerColor = AbassCardSurface,
                        labelColor = Color.White
                    )
                )
            }
        }

        // Program Timeline List
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredPrograms, key = { it.id }) { program ->
                val channel = channels.firstOrNull { it.id == program.channelId }
                val isReminderSet = reminderProgramIds.contains(program.id)

                ProgramTimelineCard(
                    program = program,
                    channelName = channel?.name ?: "ABASS TV",
                    isReminderSet = isReminderSet,
                    onToggleReminder = { onToggleReminder(program.id) },
                    onWatchChannel = { channel?.let { onWatchChannel(it) } }
                )
            }
        }
    }
}

@Composable
private fun ProgramTimelineCard(
    program: Program,
    channelName: String,
    isReminderSet: Boolean,
    onToggleReminder: () -> Unit,
    onWatchChannel: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("epg_item_${program.id}"),
        shape = RoundedCornerShape(14.dp),
        color = if (program.isLiveNow) AbassCardElevated else AbassCardSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (program.isLiveNow) AbassCyan else AbassBorder
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Time & Live indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${program.startTime} - ${program.endTime}",
                        color = if (program.isLiveNow) AbassCyan else AbassGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (program.isLiveNow) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = AbassLiveRed
                        ) {
                            Text(
                                text = "ON AIR NOW",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else if (program.hasCatchUp) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = AbassCardElevated
                        ) {
                            Text(
                                text = "CATCH UP",
                                color = AbassTextSecondary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Channel badge
                Text(
                    text = channelName,
                    color = AbassTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = program.title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = program.description,
                color = AbassTextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (program.isLiveNow) {
                LinearProgressIndicator(
                    progress = { program.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AbassCyan,
                    trackColor = AbassBorder
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Actions row: Host info & Watch Live or Set Reminder
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Anchor: ${program.host}",
                    color = AbassTextMuted,
                    fontSize = 11.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (program.isLiveNow) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AbassCyan,
                            modifier = Modifier.clickable { onWatchChannel() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LiveTv,
                                    contentDescription = "Watch Live",
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Watch Live",
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        IconButton(
                            onClick = onToggleReminder,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (isReminderSet) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                                contentDescription = "Toggle Reminder",
                                tint = if (isReminderSet) AbassCyan else AbassTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
