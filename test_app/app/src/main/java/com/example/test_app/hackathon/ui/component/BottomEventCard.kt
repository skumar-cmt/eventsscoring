package com.example.test_app.hackathon.ui.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.test_app.R
import com.example.test_app.hackathon.data.model.EventType

@Composable
fun BottomEventCard(
    eventType: EventType?,
    onViewDetailsClicked: () -> Unit
) {
    if (eventType == null) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(0.82f)
        ) {

            // Event Info Card (fills available space)
            Card(
                modifier = Modifier
                    .weight(1f),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    // Event Icon
                    Icon(
                        painter = painterResource(id = eventType.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 12.dp),
                        tint = Color.Unspecified
                    )

                    // Event Description (Score & Message)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Score drop: ")
                                }
                                withStyle(
                                    SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("${eventType.scoreDrop}")
                                }
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = eventType.description,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // View Button always stays right
                    Button(
                        onClick = onViewDetailsClicked,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color(0xFF1C77C3),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "View")
                    }
                }
            }

            // Bot Icon
//            Box(
//                modifier = Modifier
//                    .size(60.dp)
//                    .padding(bottom = 8.dp)
//                    .clickable { onViewDetailsClicked() },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.bot_1),
//                    contentDescription = "Profile Icon",
//                    modifier = Modifier.fillMaxSize(),
//                    tint = Color.Unspecified
//                )
//            }
        }
    }
}