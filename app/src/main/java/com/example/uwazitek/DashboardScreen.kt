package com.example.uwazitek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ClaimsDashboard(navController: NavHostController, username: String) {
    val allClaimsData = remember {
        listOf(
            ClaimItem("All Claims", "Total: $10,000", "View all claims", Color(0xFF4CAF50)),
            ClaimItem("Pending Claims", "Total: $3,500", "Claims awaiting approval", Color(0xFFFFC107)),
            ClaimItem("Approved Claims", "Total: $5,000", "Claims successfully approved", Color(0xFF2196F3)),
            ClaimItem("Rejected Claims", "Total: $1,500", "Claims not approved", Color(0xFFF44336))
        )
    }

    // Dropdown menu state
    var expanded by remember { mutableStateOf(false) }
    var selectedPlan by remember { mutableStateOf("Select Insurance Plan") }
    val insurancePlans = listOf("Plan A", "Plan B", "Plan C")

    // Search field state
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 56.dp), // Padding to accommodate BottomNav
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top row with user greeting and notifications
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
                    modifier = Modifier.size(32.dp)
                )

                Text(
                    text = "Hi, $username 👋",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3436),
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF2D3436),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Dropdown Menu for Insurance Plan Selection
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable { expanded = !expanded }
                    .padding(16.dp)
            ) {
                Text(
                    text = selectedPlan,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    insurancePlans.forEach { plan ->
                        DropdownMenuItem(
                            onClick = {
                                selectedPlan = plan
                                expanded = false
                            },
                            text = { Text(text = plan, fontSize = 14.sp) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search Claims") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Square Grid Layout for Claims
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Two cards per row
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f) // Takes up remaining space
            ) {
                items(allClaimsData) { claim ->
                    ClaimCard(claim = claim) {
                        println("Clicked on: ${claim.title}")
                    }
                }
            }
        }

        // Bottom Navigation Bar
        BottomNavigationBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ClaimCard(claim: ClaimItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f) // Makes the card square
            .clickable { onClick(
            ) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = claim.color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = claim.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = claim.amount,
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = claim.description,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        val items = listOf("Home", "Services", "Account")
        val icons = listOf(Icons.Default.Home, Icons.Default.FitnessCenter, Icons.Default.Settings)

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = false,
                onClick = { navController.navigate(item.lowercase()) }
            )
        }
    }
}

data class ClaimItem(val title: String, val amount: String, val description: String, val color: Color)

@Preview(showBackground = true)
@Composable
fun PreviewClaimsDashboard() {
    val navController = rememberNavController()
    ClaimsDashboard(username = "John Doe", navController = navController)
}
