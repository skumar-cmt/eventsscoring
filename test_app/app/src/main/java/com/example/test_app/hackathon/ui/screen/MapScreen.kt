package com.example.test_app.hackathon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.test_app.R
import com.example.test_app.hackathon.data.model.EventType
import com.example.test_app.hackathon.data.model.Waypoint
import com.example.test_app.hackathon.map.animator.MapAnimator
import com.example.test_app.hackathon.ui.component.BottomEventCard
import com.example.test_app.hackathon.ui.component.VideoStaticToggle
import com.example.test_app.hackathon.utils.rememberMapViewWithLifecycle
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(waypoints: List<Waypoint>, onNavigateToChatbot: (LatLng, EventType) -> Unit) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    val currentEventType = remember { mutableStateOf<EventType?>(null) }
    var animator by remember { mutableStateOf<MapAnimator?>(null) }
    val isPlaying = remember { mutableStateOf(true) }
    var refreshKey by remember { mutableStateOf(0) }
    var selectedMode by remember { mutableStateOf("Video") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { refreshKey++ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.map_marker_start),
                                contentDescription = "Start",
                                tint = Color.Unspecified,
                                modifier = Modifier.height(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Sandwich",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "-", color = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.map_marker_end),
                                contentDescription = "End",
                                tint = Color.Unspecified,
                                modifier = Modifier.height(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Plymouth",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "8.8 mi, 19 min | 7:35 PM to 7:57 PM",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFB0BEC5)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .padding(top = 80.dp, end = 16.dp)
            ) {
                if (selectedMode.equals("Video", ignoreCase = true)) {
                    Button(
                        onClick = { isPlaying.value = !isPlaying.value },
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1C77C3)
                        )
                    ) {
                        val pauseIcon = painterResource(id = R.drawable.ic_pause)
                        val playIcon = rememberVectorPainter(Icons.Default.PlayArrow)

                        Icon(
                            painter = if (isPlaying.value) pauseIcon else playIcon,
                            contentDescription = if (isPlaying.value) "Pause" else "Play",
                            tint = Color(0xFF1C77C3),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                Image(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = R.drawable.driver_color),
                    contentDescription = "Driver"
                )

                VideoStaticToggle(
                    selectedOption = selectedMode,
                    onOptionSelected = { selectedMode = it }
                )
            }

            if (selectedMode == "Static") {
                Image(
                    painter = painterResource(id = R.drawable.trip_static_img),
                    contentDescription = "Trip Summary",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize()) { view ->
                    if (animator == null) {
                        view.getMapAsync { googleMap ->
                            googleMap.uiSettings.isZoomControlsEnabled = false
                            animator =
                                MapAnimator(
                                    context,
                                    googleMap,
                                    currentEventType
                                ).also { mapAnimator ->
                                    mapAnimator.animateCarOnRoute(waypoints, isPlaying)
                                }
                        }
                    }
                }

                LaunchedEffect(waypoints) {
                    animator?.animateCarOnRoute(waypoints, isPlaying)
                }


                LaunchedEffect(refreshKey) {
                    animator?.animateCarOnRoute(waypoints, isPlaying)
                }

                Row {
                    BottomEventCard(
                        eventType = currentEventType.value,
                        onViewDetailsClicked = {
                            val position = animator?.currentCarPosition
                            val event = currentEventType.value
                            if (position != null && event != null) {
                                onNavigateToChatbot(position, event)
                            }
                        }
                    )
                }
            }
            if (selectedMode == "Video") {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 32.dp, end = 12.dp)
                        .size(60.dp)
                        .clickable {onNavigateToChatbot(LatLng(0.0,0.0), EventType.BRAKING)},
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bot_1),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}