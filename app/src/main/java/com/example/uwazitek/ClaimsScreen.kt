package com.example.uwazitek

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClaimDetailItem(val description: String, val amount: Double, val color: Color)

@Composable
fun ClaimDetailsScreen(title: String, claims: List<ClaimDetailItem>) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar() } // Add the BottomNavigationBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Top section with profile, greeting, and notifications
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color(0xFF2D3436),
                    modifier = Modifier.size(32.dp).clickable { }
                )
                Text(
                    text = "Hi, User 👋",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3436),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF2D3436),
                    modifier = Modifier.size(24.dp).clickable { }
                )
            }

            // Title
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3436),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "Search claims",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp,
                    color = Color(0xFF2D3436),
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Claims list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(claims.size) { index ->
                    val claim = claims[index]
                    ClaimRow(claim)
                }
            }

            // Total charges
            val totalCharges = claims.sumOf { it.amount }
            Text(
                text = "Total: $${totalCharges}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3436),
                modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun ClaimRow(claim: ClaimDetailItem) {
    var isHighlighted by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHighlighted) claim.color else Color(0xFFEFEFEF)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { isHighlighted = !isHighlighted },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = claim.description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2D3436)
        )
        Text(
            text = "$${claim.amount}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3436)
        )
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        val items = listOf("Home", "BMI Calc", "Services", "Account")
        val icons = listOf(
            Icons.Default.Home,
            Icons.Default.FitnessCenter,
            Icons.Default.Settings,
            Icons.Default.AccountCircle
        )
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = icons[index], contentDescription = item)
                },
                label = {
                    Text(text = item, fontSize = 12.sp)
                },
                selected = false,
                onClick = { }
            )
        }
    }
}

@Composable
fun PendingClaimsScreen() {
    val claims = listOf(
        ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge C", 180.0, Color(0xFFFFC107))
    )
    ClaimDetailsScreen("Pending Claims", claims)
}

@Composable
fun ApprovedClaimsScreen() {
    val claims = listOf(
        ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
        ClaimDetailItem("Charge Y", 450.0, Color(0xFF4CAF50)),
        ClaimDetailItem("Charge Z", 230.0, Color(0xFF4CAF50))
    )
    ClaimDetailsScreen("Approved Claims", claims)
}

@Composable
fun RejectedClaimsScreen() {
    val claims = listOf(
        ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336)),
        ClaimDetailItem("Charge N", 120.0, Color(0xFFF44336)),
        ClaimDetailItem("Charge O", 30.0, Color(0xFFF44336))
    )
    ClaimDetailsScreen("Rejected Claims", claims)
}

@Composable
fun AllClaimsScreen() {
    val claims = listOf(
        ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
        ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336))
    )
    ClaimDetailsScreen("All Claims", claims)
}

@Preview(showBackground = true)
@Composable
fun PreviewClaimDetailsScreen() {
    val claims = listOf(
        ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
        ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
        ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336))
    )
    ClaimDetailsScreen("All Claims", claims)
}