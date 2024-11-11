package com.example.uwazitek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

// Add the Claim class definition
data class Claim(
    val id: String,
    val amount: String
)

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)  // Make sure only this is used if required
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Scaffold with a TopBar and Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { scope.launch { drawerState.close() } }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle search click */ }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            content = { paddingValues ->
                // Setting up the NavHost for managing different composable destinations
                NavHost(
                    navController = navController,             // Controller to handle navigation
                    startDestination = "pending_claims"        // Initial screen shown when Scaffold is loaded
                ) {
                    // Composable destination for Pending Claims screen
                    composable(route = "pending_claims") {
                        ClaimsOverviewScreen(
                            title = "Pending Claims",           // Screen title displayed in ClaimsOverviewScreen
                            claims = listOf(
                                Claim("claim#7505", "$5000"),   // Example claims data for Pending Claims
                                Claim("claim#8448", "$2000")
                            ),
                            paddingValues = paddingValues       // Pass padding to avoid overlapping with top/bottom bars
                        )
                    }

                    // Composable destination for Approved Claims screen
                    composable(route = "approved_claims") {
                        ClaimsOverviewScreen(
                            title = "Approved Claims",
                            claims = listOf(
                                Claim("claim#4649", "$1500"),
                                Claim("claim#8754", "N/A")
                            ),
                            paddingValues = paddingValues
                        )
                    }

                    // Composable destination for Rejected Claims screen
                    composable(route = "rejected_claims") {
                        ClaimsOverviewScreen(
                            title = "Rejected Claims",
                            claims = listOf(
                                Claim("claim#1030", "$3000"),
                                Claim("claim#2099", "$800")
                            ),
                            paddingValues = paddingValues
                        )
                    }

                    // Composable destination for Settings screen
                    composable(route = "settings") {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(16.dp)
                        ) {
                            Text(text = "Settings Screen")      // Placeholder content for the Settings screen
                        }
                    }
                }
            }


        )
    }
}

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Dashboard",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Pending",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Approved",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Closed",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Settings",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCloseDrawer() }
        )
    }
}

@Composable
fun ClaimsOverviewScreen(title: String, claims: List<Claim>, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)  // Applying paddingValues here
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display Claims Cards
        claims.forEach { claim ->
            ClaimsCard(sectionTitle = claim.id, totalAmount = claim.amount)
        }
    }
}

@Composable
fun ClaimsCard(sectionTitle: String, totalAmount: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color(0xFF6200EE), // Customize as needed
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pending Claims") },
//            label = { Text("Pending") },
            selected = false, // You can manage the selected state based on navigation
            onClick = {
                navController.navigate("pending_claims") // Navigate to Pending Claims Screen
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Approved Claims") },
//            label = { Text("Approved") },
            selected = false,
            onClick = {
                navController.navigate("approved_claims") // Navigate to Approved Claims Screen
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Rejected Claims") }, // Using Block instead of Cancel
//            label = { Text("Rejected") },
            selected = false,
            onClick = {
                navController.navigate("rejected_claims") // Navigate to Rejected Claims Screen
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
//            label = { Text("Settings") },
            selected = false,
            onClick = {
                navController.navigate("settings") // Navigate to Settings Screen
            }
        )
    }
}
