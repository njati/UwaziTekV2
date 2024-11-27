package com.example.uwazitek.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uwazitek.HealthService

@Composable
fun ServiceCard(service: HealthService) {
    // State to manage expanded/compressed status of the card
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded }, // Toggle expanded/compressed on click
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = service.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Hospital: ${service.hospital}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            if (expanded) {
                // Additional details when expanded
                Text(
                    text = "Location: ${service.location}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Hours: ${service.hours}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Cost: ${service.cost}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Phone: ${service.phone}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Rating: ${service.rating} / 5.0",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServiceCard() {
    ServiceCard(service = HealthService("Service 1", "Hospital A", "Location A", "9 AM - 5 PM", "$200", "123-456-7890", 4.5f))
}
