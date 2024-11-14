package com.example.uwazitek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
//Services
data class HealthService(
    val name: String,
    val hospital: String,
    val location: String,
    val hours: String,
    val cost: String,
    val phone: String,
    val rating: Float
)

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Track current route
    var currentRoute by remember { mutableStateOf("pending_claims") }

    // Track search query
    var searchQuery by remember { mutableStateOf("") }

    // Update currentRoute when the navigation destination changes
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route ?: "pending_claims"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(navController = navController, onCloseDrawer = { scope.launch { drawerState.close() } })
            },
            modifier = Modifier.background(Color.Gray)
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (currentRoute != "settings") {
                                    Text("UwaziTek", color = Color.White)
                                } else {
                                    // Leave blank or put another title when on the Settings screen
                                    Text("", color = Color.White)
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            if (currentRoute != "settings") {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 16.dp)
                                ) {
                                    IconButton(onClick = { /* Handle search click */ }) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = "Search",
                                            tint = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp)) // Adds space between icons
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        placeholder = { Text("Search Claims", color = Color.Gray) },
                                        modifier = Modifier
                                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                                            .fillMaxWidth(0.75f) // Adjusts TextField width to fit better
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }

                    )
                },
                bottomBar = {
                    BottomNavigationBar(navController)
                },
                containerColor = Color.White,
                content = { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "pending_claims"
                    ) {
                        composable(route = "pending_claims") {
                            ClaimsOverviewScreen(
                                title = "Pending Claims",
                                claims = listOf(
                                    Claim("claim#7505", "$5000"),
                                    Claim("claim#8448", "$2000")
                                ).filter { it.id.contains(searchQuery, ignoreCase = true) || it.amount.contains(searchQuery, ignoreCase = true) },
                                paddingValues = paddingValues
                            )
                        }

                        composable(route = "approved_claims") {
                            ClaimsOverviewScreen(
                                title = "Approved Claims",
                                claims = listOf(
                                    Claim("claim#4649", "$1500"),
                                    Claim("claim#8754", "N/A")
                                ).filter { it.id.contains(searchQuery, ignoreCase = true) || it.amount.contains(searchQuery, ignoreCase = true) },
                                paddingValues = paddingValues
                            )
                        }

                        composable(route = "rejected_claims") {
                            ClaimsOverviewScreen(
                                title = "Rejected Claims",
                                claims = listOf(
                                    Claim("claim#1030", "$3000"),
                                    Claim("claim#2099", "$800")
                                ).filter { it.id.contains(searchQuery, ignoreCase = true) || it.amount.contains(searchQuery, ignoreCase = true) },
                                paddingValues = paddingValues,
                            )
                        }
                        composable(route = "services") {
                            ServicesScreen(paddingValues)
                        }
                        composable(route = "settings") {
                            SettingsScreen(paddingValues)
                        }
                    }
                }
            )
        }
    }
}

// Updated Settings screen function
@Composable
fun SettingsScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Profile Picture Section
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.Gray)
                .clickable { /* Handle profile picture update */ },
                contentAlignment = Alignment.Center
        ) {
            Text("Profile", color = Color.White)
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Update Password Option
        SettingsOption(
            label = "Update Password",
            onClick = { /* Navigate to update password screen */ }
        )

        // Contact Support Chat Button
        SettingsOption(
            label = "Contact Support",
            onClick = { /* Open chat for support */ }
        )

        // Logout Button
        Button(
            onClick = { /* Handle logout functionality */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

// Helper composable for each settings option
@Composable
fun SettingsOption(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Filled.Settings, contentDescription = "Go to $label")
    }
}

@Composable
fun DrawerContent(navController: NavController, onCloseDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight(2 / 3f)
            .fillMaxWidth(0.5f)
            .padding(vertical = 16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "UwaziTek",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
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
                .clickable {
                    navController.navigate("pending_claims") // Navigate to Pending Claims
                    onCloseDrawer()
                }
        )
        Text(
            text = "Approved",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    navController.navigate("approved_claims") // Navigate to Approved Claims
                    onCloseDrawer()
                }
        )
        Text(
            text = "Closed",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    navController.navigate("rejected_claims") // Navigate to Rejected Claims
                    onCloseDrawer()
                }
        )
        Text(
            text = "Services",
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("services"); onCloseDrawer() })
        Text(
            text = "Settings",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    navController.navigate("settings") // Navigate to Settings
                    onCloseDrawer()
                }
        )
    }
}

@Composable
fun ClaimsOverviewScreen(title: String, claims: List<Claim>, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pending Claims") },
            selected = false,
            onClick = { navController.navigate("pending_claims") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Approved Claims") },
            selected = false,
            onClick = { navController.navigate("approved_claims") }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.baseline_cancel_24), contentDescription = "Rejected Claims") },
            selected = false,
            onClick = { navController.navigate("rejected_claims") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
    }
}

@Composable
fun ServicesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = "Available Health Services",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val services = listOf(
            HealthService("Lab Test", "City Hospital", "Downtown", "8AM - 5PM", "$50", "123-456-7890", 4.5f),
            HealthService("Dentist", "Smile Clinic", "Uptown", "9AM - 6PM", "$75", "098-765-4321", 4.8f)
            // Additional services
        )
        services.forEach { service ->
            ServiceCard(service)
        }
    }
}

@Composable
fun ServiceCard(service: HealthService) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle service click */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = service.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Hospital: ${service.hospital}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Location: ${service.location}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Hours: ${service.hours}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Cost: ${service.cost}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Contact: ${service.phone}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Rating: ${service.rating} stars", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

