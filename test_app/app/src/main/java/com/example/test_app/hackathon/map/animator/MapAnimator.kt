package com.example.test_app.hackathon.map.animator

import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.MutableState
import com.example.test_app.R
import com.example.test_app.hackathon.data.model.EventType
import com.example.test_app.hackathon.data.model.Waypoint
import com.example.test_app.hackathon.utils.getBearing
import com.example.test_app.hackathon.utils.vectorToBitmapDescriptor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapAnimator(
    private val context: Context,
    private val map: GoogleMap,
    private val currentEventType: MutableState<EventType?>
) {
    private var carMarker: Marker? = null
    private var routePolyline: Polyline? = null
    private var animationJob: Job? = null
    private var lastCarPosition: LatLng? = null

    val currentCarPosition: LatLng?
        get() = lastCarPosition

    fun animateCarOnRoute(
        waypoints: List<Waypoint>,
        isPlaying: MutableState<Boolean>,
    ) {
        val path = waypoints.map { it.position }
        if (path.size < 2) return

        clearPreviousRoute()

        // Draw polyline segments based on events
        for (i in 0 until waypoints.size - 1) {
            val eventType = waypoints[i + 1].eventType
            lastCarPosition = waypoints[i].position

            val segmentColor = when (eventType) {
                EventType.SPEEDING -> Color.RED
                EventType.PHONE_DISTRACTION -> Color.BLUE
                else -> Color.GREEN
            }

            map.addPolyline(
                PolylineOptions()
                    .add(waypoints[i].position, waypoints[i + 1].position)
                    .width(18f)
                    .color(segmentColor)
            )
        }

        map.addMarker(
            MarkerOptions()
                .position(path.first())
                .title("Start")
                .icon(vectorToBitmapDescriptor(context, R.drawable.map_marker_start))
        )
        map.addMarker(
            MarkerOptions()
                .position(path.last())
                .title("End")
                .icon(vectorToBitmapDescriptor(context, R.drawable.map_marker_end))
        )

        carMarker = map.addMarker(
            MarkerOptions()
                .position(path.first())
                .flat(true)
                .anchor(0.5f, 0.5f)
                .icon(vectorToBitmapDescriptor(context, R.drawable.car))
        )

        val bounds =
            LatLngBounds.Builder().also { builder -> path.forEach(builder::include) }.build()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.center, 17f))

        animationJob = CoroutineScope(Dispatchers.Main).launch {
            var waypointIndex = 0
            while (waypointIndex < path.size - 1) {
                val start = path[waypointIndex]
                val end = path[waypointIndex + 1]
                val steps = 10

                for (step in 0..steps) {
                    while (!isPlaying.value) {
                        delay(2000)
                    }
                    val lat = start.latitude + (end.latitude - start.latitude) * step / steps
                    val lng = start.longitude + (end.longitude - start.longitude) * step / steps
                    val pos = LatLng(lat, lng)

                    carMarker?.position = pos
                    carMarker?.rotation = getBearing(start, end)
                    lastCarPosition = pos
                    delay(30L)
                }

                waypoints[waypointIndex + 1].eventType?.let { event ->
                    currentEventType.value = when (event.name.uppercase()) {
                        "BRAKING" -> EventType.BRAKING
                        "ACCELERATION" -> EventType.ACCELERATION
                        "CORNERING" -> EventType.CORNERING
                        "SPEEDING" -> EventType.SPEEDING
                        else -> EventType.PHONE_DISTRACTION
                    }
                    delay(2000L)
                    if (isPlaying.value) currentEventType.value = null
                }

                waypointIndex++
            }
        }
    }

    private fun clearPreviousRoute() {
        animationJob?.cancel()
        routePolyline?.remove()
        carMarker?.remove()
        map.clear()
    }
}
