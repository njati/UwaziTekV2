package com.example.uwazitek

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.api.APIService
import com.example.uwazitek.components.BottomNavigationBar
import com.example.uwazitek.components.ClaimsOverviewScreen
import com.example.uwazitek.components.DrawerContent
import com.example.uwazitek.components.ServicesScreen
import com.example.uwazitek.components.SettingsScreen
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
            DashboardScreen(globalNav = rememberNavController())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(globalNav: NavController) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var currentRoute by remember { mutableStateOf("pending_claims") }
    var searchQuery by remember { mutableStateOf("") }

    var context = LocalContext.current

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route ?: "pending_claims"
        }

        var response = APIService.getDashboardService(context).getDashboardStat().body()
        Log.d("Dashboard", response.toString())

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
                                    Text("", color = Color.White)
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6495ED)))},
                bottomBar = {
                    BottomNavigationBar(navController)
                },
                containerColor = Color.White,
                content = { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "pending_claims"
                    )
                    {
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
                            SettingsScreen(navController, paddingValues)                      }
                    }
                }
            )
        }
    }
}

// Search Composable
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(placeholderText, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen(globalNav = rememberNavController())
}