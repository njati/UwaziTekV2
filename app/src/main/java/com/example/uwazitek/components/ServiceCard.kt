package com.example.uwazitek.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uwazitek.api.dto.HealthService

@Composable
fun ServiceCard(service: HealthService) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(animationSpec = tween(300)) // Smooth expand/collapse animation
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        // Use a Box to apply the gradient background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2196F3), Color(0xFF21CBF3)) // Gradient colors
                    )
                )
                .padding(16.dp) // Inner padding
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = service.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Visible against the gradient
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand/Collapse",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Hospital: ${service.hospital}",
                    fontSize = 16.sp,
                    color = Color.White
                )

                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailRow(Icons.Default.Place, "Location: ${service.location}")
                    DetailRow(Icons.Default.AccessTime, "Hours: ${service.hours}")
                    DetailRow(Icons.Default.AttachMoney, "Cost: ${service.cost}")
                    DetailRow(Icons.Default.Phone, "Phone: ${service.phone}")
                    DetailRow(Icons.Default.Star, "Rating: ${service.rating} / 5.0")
                }
            }
        }
    }
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServiceCard() {
    ServiceCard(
        service = HealthService(
            "Dentist",
            "Kenyatta National Hospital",
            "Nairobi",
            "8 AM - 6 PM",
            "$100",
            "0712-345-678",
            4.2f
        )
    )
}
