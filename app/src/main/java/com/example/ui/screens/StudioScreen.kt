package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SettingsInputAntenna
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.MockTvRepository
import com.example.data.NewsTipSubmission
import com.example.data.SatelliteInfo
import com.example.ui.theme.AbassBorder
import com.example.ui.theme.AbassCardElevated
import com.example.ui.theme.AbassCardSurface
import com.example.ui.theme.AbassCyan
import com.example.ui.theme.AbassDarkBlue
import com.example.ui.theme.AbassGold
import com.example.ui.theme.AbassGreen
import com.example.ui.theme.AbassMidnight
import com.example.ui.theme.AbassTextMuted
import com.example.ui.theme.AbassTextSecondary

@Composable
fun StudioScreen(
    isAudioOnly: Boolean,
    onToggleAudioOnly: () -> Unit,
    onSubmitTip: (NewsTipSubmission) -> Unit,
    tipStatusMessage: String?,
    onClearTipStatus: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var reporterName by remember { mutableStateOf("") }
    var reporterEmail by remember { mutableStateOf("") }
    var reporterLocation by remember { mutableStateOf("") }
    var storyTopic by remember { mutableStateOf("") }
    var storyDetails by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AbassMidnight),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Station ID Header
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("station_header_card"),
                shape = RoundedCornerShape(16.dp),
                color = AbassCardSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_abass_logo_1793121431672),
                            contentDescription = "ABASS TV Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ABASS TV NETWORK",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "Global Television & Digital Broadcast",
                                color = AbassCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Broadcasting 24/7 across Satellite, Cable & Mobile",
                                color = AbassTextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = AbassBorder)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("ABASS TV Email", "abasstv70@gmail.com")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Email copied: abasstv70@gmail.com", Toast.LENGTH_SHORT).show()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AlternateEmail,
                                contentDescription = null,
                                tint = AbassGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(text = "Official Station Contact", color = AbassTextMuted, fontSize = 10.sp)
                                Text(text = "abasstv70@gmail.com", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy email",
                            tint = AbassCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Data Saver Toggle
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = AbassCardSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isAudioOnly) AbassCyan.copy(alpha = 0.2f) else AbassCardElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = null,
                                tint = if (isAudioOnly) AbassCyan else Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Low-Bandwidth Audio Stream",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Streams studio audio only to conserve 85% mobile data",
                                color = AbassTextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = isAudioOnly,
                        onCheckedChange = { onToggleAudioOnly() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = AbassCyan
                        )
                    )
                }
            }
        }

        // Satellite Frequency Directory
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SatelliteAlt,
                        contentDescription = null,
                        tint = AbassCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "SATELLITE FREQUENCY & TRANSPONDERS",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                MockTvRepository.satelliteParameters.forEach { sat ->
                    SatelliteParameterCard(satellite = sat)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Direct Newsroom Tip & Story Submission Form
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("news_tip_form"),
                shape = RoundedCornerShape(16.dp),
                color = AbassCardSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            tint = AbassGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "SUBMIT NEWS TIP OR STORY",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Witnessed breaking news or have an investigative lead? Direct dispatch to ABASS TV Newsroom.",
                        color = AbassTextSecondary,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = reporterName,
                        onValueChange = { reporterName = it },
                        label = { Text("Your Full Name (or Anonymous)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("tip_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = inputFieldColors()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = reporterLocation,
                            onValueChange = { reporterLocation = it },
                            label = { Text("City / Country") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = inputFieldColors()
                        )
                        OutlinedTextField(
                            value = storyTopic,
                            onValueChange = { storyTopic = it },
                            label = { Text("Topic / Headline") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = inputFieldColors()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = storyDetails,
                        onValueChange = { storyDetails = it },
                        label = { Text("Story Details, Date & Location...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("tip_details_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = inputFieldColors()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (storyDetails.isNotBlank()) {
                                onSubmitTip(
                                    NewsTipSubmission(
                                        fullName = reporterName.ifBlank { "Anonymous Reporter" },
                                        email = reporterEmail.ifBlank { "abasstv70@gmail.com" },
                                        phone = "",
                                        location = reporterLocation.ifBlank { "Mobile App Dispatch" },
                                        topic = storyTopic.ifBlank { "News Lead" },
                                        storyDetails = storyDetails
                                    )
                                )
                                reporterName = ""
                                storyTopic = ""
                                storyDetails = ""
                                reporterLocation = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_tip_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AbassCyan)
                    ) {
                        Text(
                            text = "Transmit to Newsroom Desk",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }

    // Success dialog after tip submission
    tipStatusMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = onClearTipStatus,
            containerColor = AbassDarkBlue,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = AbassGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Transmission Confirmed", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Text(text = msg, color = AbassTextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
            },
            confirmButton = {
                Button(
                    onClick = onClearTipStatus,
                    colors = ButtonDefaults.buttonColors(containerColor = AbassCyan)
                ) {
                    Text("Dismiss", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
private fun SatelliteParameterCard(satellite: SatelliteInfo) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = AbassCardSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, AbassBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = satellite.satelliteName,
                    color = AbassCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = satellite.orbitalPosition,
                    color = AbassGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Frequency", color = AbassTextMuted, fontSize = 10.sp)
                    Text(text = satellite.frequency, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text(text = "Polarization", color = AbassTextMuted, fontSize = 10.sp)
                    Text(text = satellite.polarization, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text(text = "Symbol Rate", color = AbassTextMuted, fontSize = 10.sp)
                    Text(text = satellite.symbolRate, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text(text = "FEC", color = AbassTextMuted, fontSize = 10.sp)
                    Text(text = satellite.fec, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Coverage: ${satellite.coverage} • ${satellite.modulation}",
                color = AbassTextSecondary,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun inputFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = AbassCardElevated,
    unfocusedContainerColor = AbassCardElevated,
    focusedBorderColor = AbassCyan,
    unfocusedBorderColor = AbassBorder,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = AbassCyan,
    unfocusedLabelColor = AbassTextMuted
)
