package com.example.test_app.hackathon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test_app.hackathon.data.model.EventType
import com.example.test_app.hackathon.data.model.Waypoint
import com.example.test_app.hackathon.ui.screen.ChatbotScreen
import com.example.test_app.hackathon.ui.screen.MapScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun AppNavigation(waypoints: List<Waypoint>) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "map_screen") {
        composable("map_screen") {
            MapScreen(
                waypoints = waypoints,
                onNavigateToChatbot = { position, eventType ->
                    val lat = position.latitude
                    val lng = position.longitude
                    val type = eventType.name
                    navController.navigate("chatbot_screen/$lat,$lng,$type")
                }
            )
        }

        composable(
            route = "chatbot_screen/{lat},{lng},{eventType}",
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType },
                navArgument("lng") { type = NavType.StringType },
                navArgument("eventType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
            val lng = backStackEntry.arguments?.getString("lng")?.toDoubleOrNull()
            val eventTypeName = backStackEntry.arguments?.getString("eventType")

            val latLng = if (lat != null && lng != null) LatLng(lat, lng) else null
            val eventType = eventTypeName?.let {
                runCatching { EventType.valueOf(it) }.getOrNull()
            }

            ChatbotScreen(
                currentPosition = latLng,
                currentEventType = eventType,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}