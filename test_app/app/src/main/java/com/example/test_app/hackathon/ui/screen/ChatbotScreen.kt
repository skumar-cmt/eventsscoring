package com.example.test_app.hackathon.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_app.hackathon.data.model.EventType
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.test_app.R
import com.example.test_app.hackathon.data.model.ChatMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(
    currentPosition: LatLng?,
    currentEventType: EventType?,
    onBackPressed: () -> Unit
) {
    val questions = remember(currentEventType) {
        if (currentPosition?.latitude == 0.0 && currentPosition.longitude == 0.0) {
            listOf(
                "Check my event based score.",
                "Explore ‘What if’ scores."
            )
        } else {
            when (currentEventType) {
                EventType.BRAKING -> listOf(
                    "Why did it mark that as a hard brake?",
                    "Was that really a dangerous brake?",
                )

                EventType.CORNERING -> listOf(
                    "Why was that turn considered sharp?",
                    "Was I going too fast into the curve?",
                )

                EventType.PHONE_DISTRACTION -> listOf(
                    "Why did it say I was distracted?",
                    "What counts as a distraction?",
                )

                EventType.SPEEDING -> listOf(
                    "Where exactly was I speeding?",
                    "By how much did I go over the limit?",
                )

                EventType.ACCELERATION -> listOf(
                    "What made that count as harsh acceleration?",
                    "Was I accelerating too fast or just merging?",
                )

                else -> emptyList()
            }
        }
    }

    var messageText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val messages = remember { mutableStateListOf<ChatMessage>() }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Bot icon in circle

                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.size(36.dp),
                            color = Color.Transparent
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bot_1),
                                contentDescription = "Bot Icon",
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "TeleMigo",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.map_marker_start),
                                    contentDescription = "Start",
                                    modifier = Modifier.size(12.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Sandwich",
                                    color = Color(0xFFB0BEC5),
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "-", color = Color(0xFFB0BEC5),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.map_marker_end),
                                    contentDescription = "End",
                                    modifier = Modifier.size(12.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Plymouth", color = Color(0xFFB0BEC5),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF4F4F4), RoundedCornerShape(24.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Ask me regarding your trip") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1
                )
                IconButton(onClick = {
                    if (messageText.isNotBlank()) {
                        coroutineScope.launch {
                            messages.add(ChatMessage(text = messageText, isFromUser = true))
                            sendMessageToBackend(messageText)
                            val response = receiveMessageFromBackend(messageText)
                            messages.add(ChatMessage(text = response, isFromUser = false))
                            messageText = ""
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color(0xFF1976D2)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                questions.forEach { question ->
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFFE3F2FD),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    messages.add(ChatMessage(text = question, isFromUser = true))
                                    val response = receiveMessageFromBackend(question)
                                    messages.add(ChatMessage(text = response, isFromUser = false))
                                }
                            }
                            .padding(vertical = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = question,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Send this",
                                tint = Color(0xFF1976D2)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                items(messages) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isFromUser) Arrangement.Start else Arrangement.End
                    ) {
                        Surface(
                            color = if (msg.isFromUser) Color(0xFFE0F7FA) else Color(0xFFBBDEFB),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

suspend fun sendMessageToBackend(message: String) {
    Log.d("Chatbot", "Sending message: $message")
    delay(500)
}


suspend fun receiveMessageFromBackend(userMessage: String): String {
    delay(500)
    return when {
        userMessage.contains("speed", ignoreCase = true) ->
            "Based on your trip, you exceeded the speed limit by 12 km/h."

        userMessage.contains("brake", ignoreCase = true) ->
            "We detected a hard brake due to sudden deceleration."

        userMessage.contains("score", ignoreCase = true) ->
            "This event impacted your safety score by -15 points."

        userMessage.contains("distraction", ignoreCase = true) ->
            "Your phone was active while driving during this event."

        userMessage.contains("corner", ignoreCase = true) ->
            "That corner was taken too sharply at a high speed."

        else ->
            "Thank you for your question. We’ll get back with more details."
    }
}