package com.example.test_app.hackathon.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VideoStaticToggle(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Video", "Static")

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF0F0F0),
        border = BorderStroke(1.dp, Color.Transparent),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { option ->
                val isSelected = selectedOption == option
                val backgroundColor = if (isSelected) Color(0xFF1C77C3) else Color.Transparent
                val textColor = if (isSelected) Color.White else Color(0xFF1C77C3)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(backgroundColor)
                        .clickable { onOptionSelected(option) }
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = option,
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
