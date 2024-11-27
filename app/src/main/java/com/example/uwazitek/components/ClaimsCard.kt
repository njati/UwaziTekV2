package com.example.uwazitek.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun ClaimsCard(sectionTitle: String, totalAmount: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = sectionTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Total Amount: $totalAmount",
                fontSize = 16.sp,
                color = Color.Gray
            )

            if (expanded) {
                Text(
                    text = "Insurance Company: XYZ Insurance",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Claim Description: Full health coverage for 2023",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Date Filed: 2023-06-15",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Button(
                    onClick = { /* Open PDF */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("View PDF")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClaimsCard() {
    ClaimsCard(sectionTitle = "Claim1", totalAmount = "100")
}