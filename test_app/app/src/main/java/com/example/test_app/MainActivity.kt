package com.example.test_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.test_app.hackathon.data.model.EventType
import com.example.test_app.hackathon.data.model.Waypoint
import com.example.test_app.hackathon.navigation.AppNavigation
import com.example.test_app.hackathon.ui.screen.MapScreen
import com.example.test_app.ui.theme.Test_appTheme
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Required for Google Maps
        MapsInitializer.initialize(applicationContext)

        val waypoints = listOf(
            Waypoint(LatLng(42.36228, -71.0871)),
            Waypoint(LatLng(42.36231, -71.0871)),
            Waypoint(LatLng(42.36234, -71.0871)),
            Waypoint(LatLng(42.36236, -71.08709), EventType.ACCELERATION),
            Waypoint(LatLng(42.36239, -71.08709)),
            Waypoint(LatLng(42.36242, -71.08709)),
            Waypoint(LatLng(42.36244, -71.08709), EventType.ACCELERATION),
            Waypoint(LatLng(42.36247, -71.08708)),
            Waypoint(LatLng(42.3625, -71.08708)),
            Waypoint(LatLng(42.36249, -71.087), EventType.BRAKING),
            Waypoint(LatLng(42.36249, -71.08693)),
            Waypoint(LatLng(42.36248, -71.08685)),
            Waypoint(LatLng(42.36248, -71.08677)),
            Waypoint(LatLng(42.36247, -71.08669)),
            Waypoint(LatLng(42.36247, -71.08662)),
            Waypoint(LatLng(42.36246, -71.08654)),
            Waypoint(LatLng(42.36245, -71.08646)),
            Waypoint(LatLng(42.36245, -71.08638)),
            Waypoint(LatLng(42.36244, -71.08631)),
            Waypoint(LatLng(42.36244, -71.08623)),
            Waypoint(LatLng(42.36243, -71.08615), EventType.ACCELERATION),
            Waypoint(LatLng(42.36242, -71.08607)),
            Waypoint(LatLng(42.36242, -71.08599)),
            Waypoint(LatLng(42.36241, -71.08592)),
            Waypoint(LatLng(42.36241, -71.08584)),
            Waypoint(LatLng(42.3624, -71.08576)),
            Waypoint(LatLng(42.36239, -71.08568)),
            Waypoint(LatLng(42.36239, -71.08561)),
            Waypoint(LatLng(42.36238, -71.08553)),
            Waypoint(LatLng(42.36238, -71.08545)),
            Waypoint(LatLng(42.36237, -71.08537)),
            Waypoint(LatLng(42.36237, -71.0853)),
            Waypoint(LatLng(42.36236, -71.08522)),
            Waypoint(LatLng(42.36232, -71.08516)),
            Waypoint(LatLng(42.36231, -71.08508), EventType.ACCELERATION),
            Waypoint(LatLng(42.3623, -71.085)),
            Waypoint(LatLng(42.36229, -71.08492)),
            Waypoint(LatLng(42.36228, -71.08485)),
            Waypoint(LatLng(42.36227, -71.08477)),
            Waypoint(LatLng(42.36226, -71.08469)),
            Waypoint(LatLng(42.36225, -71.08461)),
            Waypoint(LatLng(42.36224, -71.08453)),
            Waypoint(LatLng(42.36223, -71.08445)),
            Waypoint(LatLng(42.36222, -71.08438), EventType.SPEEDING),
            Waypoint(LatLng(42.36221, -71.0843), EventType.SPEEDING)
        )

        setContent {
            Test_appTheme {
                AppNavigation(waypoints)
            }
        }
//        setContent {
//            Test_appTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
//                    Box(modifier = Modifier.fillMaxSize()) {
//                        MapScreen(waypoints)
//                    }
//                }
//            }
//        }
    }
}