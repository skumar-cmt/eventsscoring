package com.example.test_app.hackathon.data.model

import com.google.android.gms.maps.model.LatLng

data class Waypoint(
    val position: LatLng,
    val eventType: EventType? = null
)