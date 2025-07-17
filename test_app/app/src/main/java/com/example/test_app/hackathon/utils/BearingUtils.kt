package com.example.test_app.hackathon.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun getBearing(start: LatLng, end: LatLng): Float {
    val lat1 = Math.toRadians(start.latitude)
    val lng1 = Math.toRadians(start.longitude)
    val lat2 = Math.toRadians(end.latitude)
    val lng2 = Math.toRadians(end.longitude)

    val dLng = lng2 - lng1
    val y = sin(dLng) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLng)

    return ((Math.toDegrees(atan2(y, x)) + 360) % 360).toFloat()
}