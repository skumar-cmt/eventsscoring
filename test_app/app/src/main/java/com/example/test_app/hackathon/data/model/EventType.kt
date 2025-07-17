package com.example.test_app.hackathon.data.model

import androidx.annotation.DrawableRes
import com.example.test_app.R


enum class EventType(val description: String, @DrawableRes val iconRes: Int, val scoreDrop: Int) {
    BRAKING("Braked too hard in downhill", R.drawable.map_marker_braking, -10),
    ACCELERATION("Rapid acceleration at Elm’s Street when compared to your past records", R.drawable.map_marker_accel, -3),
    CORNERING("Turning was too sharp which is 36.5 deg/sec @15mph", R.drawable.map_marker_cornering, -5),
    SPEEDING("Highest speeding record in your history, detected here.", R.drawable.map_marker_speeding, -20),
    PHONE_DISTRACTION("Using mobile phones while driving leads to accidents", R.drawable.map_marker_distraction, -10),
}