package com.example.uwazitek

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.uwazitek.api.APIService
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)                    )
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
                            SettingsScreen (paddingValues)
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
    var showContactSupportPopup by remember { mutableStateOf(false) }
    var showUpdatePasswordPopup by remember { mutableStateOf(false) }
    var showUpdateEmailPopup by remember { mutableStateOf(false) }
    var showUpdatePhonePopup by remember { mutableStateOf(false) }
    var showChangeProfilePicPopup by remember { mutableStateOf(false) }
    var showChatButton by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center elements vertically
    ) {
        // Profile Picture at the Top Center
        Image(
            painter = rememberAsyncImagePainter("your_image_url_or_drawable_resource"),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { showChangeProfilePicPopup = true } // Opens profile picture change popup
                .background(Color.Gray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Options
        // Contact Support with Confirmation Field
        Button(onClick = { showContactSupportPopup = true }) {
            Text("Contact Support")
        }
        if (showContactSupportPopup) {
            PopupDialog(
                onDismiss = { showContactSupportPopup = false },
                content = {}
            )
            showChatButton = true
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Update Password with Confirmation Field
        Button(onClick = { showUpdatePasswordPopup = true }) {
            Text("Update Password")
        }
        if (showUpdatePasswordPopup) {
            PopupDialog(
                onDismiss = { showUpdatePasswordPopup = false },
                content = { PasswordUpdateContent() } // Adds confirmation field
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Update Email with Confirmation Field
        Button(onClick = { showUpdateEmailPopup = true }) {
            Text("Update Email")
        }
        if (showUpdateEmailPopup) {
            PopupDialog(
                onDismiss = { showUpdateEmailPopup = false },
                content = { EmailUpdateContent() } // Adds confirmation field
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Update Phone Number with Confirmation Field
        Button(onClick = { showUpdatePhonePopup = true }) {
            Text("Update Phone Number")
        }
        if (showUpdatePhonePopup) {
            PopupDialog(
                onDismiss = { showUpdatePhonePopup = false },
                content = { PhoneUpdateContent() } // Adds confirmation field
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Change Profile Picture (No Confirmation Field Needed)
        Button(onClick = { showChangeProfilePicPopup = true }) {
            Text("Change Profile Picture")
        }
        if (showChangeProfilePicPopup) {
            PopupDialog(
                onDismiss = { showChangeProfilePicPopup = false },
                content = { ProfilePicUpdateContent() }
            )
        }
    }

    // Chat Button (Appears at Bottom Right)
    if (showChatButton) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = { /* Handle chat support functionality */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Chat, contentDescription = "Chat Support")
            }
        }
    }
}

// PopupDialog composable
@Composable
fun PopupDialog(onDismiss: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(8.dp)), // Use shadow instead of elevation
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    }
}

// Composable functions for each popup content
@Composable
fun PasswordUpdateContent() {
    var newPassword by remember { mutableStateOf("") }
    Column {
        Text(text = "Update Password")
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { /* Handle password update */ }) {
            Text("Update Password")
        }
    }
}

@Composable
fun EmailUpdateContent() {
    var newEmail by remember { mutableStateOf("") }
    Column {
        Text(text = "Update Email")
        TextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            label = { Text("New Email") }
        )
        Button(onClick = { /* Handle email update */ }) {
            Text("Update Email")
        }
    }
}

@Composable
fun PhoneUpdateContent() {
    var newPhone by remember { mutableStateOf("") }
    Column {
        Text(text = "Update Phone Number")
        TextField(
            value = newPhone,
            onValueChange = { newPhone = it },
            label = { Text("New Phone Number") }
        )
        Button(onClick = { /* Handle phone update */ }) {
            Text("Update Phone")
        }
    }
}

@Composable
fun ProfilePicUpdateContent() {
    Column {
        Text(text = "Change Profile Picture")
        Button(onClick = { /* Handle profile picture update */ }) {
            Text("Choose Picture")
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
    // Changes: Full screen height and half screen width for the drawer
    Column(
        modifier = Modifier
            .fillMaxHeight()      // Cover entire screen height
            .fillMaxWidth(0.5f)   // Cover half screen width
            .background(Color.White)
    ) {
        Text(
            text = "UwaziTek",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { onCloseDrawer() }
        )

        // Enhanced text styling and padding for menu items
        DrawerItem(
            text = "Pending",
            navController = navController,
            route = "pending_claims",
            onCloseDrawer = onCloseDrawer
        )

        DrawerItem(
            text = "Approved",
            navController = navController,
            route = "approved_claims",
            onCloseDrawer = onCloseDrawer
        )

        DrawerItem(
            text = "Closed",
            navController = navController,
            route = "rejected_claims",
            onCloseDrawer = onCloseDrawer
        )

        DrawerItem(
            text = "Services",
            navController = navController,
            route = "services",
            onCloseDrawer = onCloseDrawer
        )

        DrawerItem(
            text = "Settings",
            navController = navController,
            route = "settings",
            onCloseDrawer = onCloseDrawer
        )
    }
}

@Composable
fun DrawerItem(text: String, navController: NavController, route: String, onCloseDrawer: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp)
            .clickable {
                navController.navigate(route)
                onCloseDrawer()
            }
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ClaimsOverviewScreen(title: String, claims: List<Claim>, paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Display the title
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display the search bar below the title
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Claims", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        )

        // LazyColumn to display filtered claims
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Take up available space
        ) {
            items(claims.filter {
                it.id.contains(searchQuery, ignoreCase = true) || it.amount.contains(searchQuery, ignoreCase = true)
            }) { claim ->
                ClaimsCard(sectionTitle = claim.id, totalAmount = claim.amount)
            }
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
        contentColor = Color.White,
        backgroundColor = Color.White
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
    var searchQuery by remember { mutableStateOf("") }

    // Sample data for services
    val services = listOf(
        HealthService("Service 1", "Hospital A", "Location A", "9 AM - 5 PM", "$200", "123-456-7890", 4.5f),
        HealthService("Service 2", "Hospital B", "Location B", "24/7", "$150", "098-765-4321", 4.0f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Services", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        )

        // LazyColumn for services
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(services.filter {
                it.name.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
            }) { service ->
                ServiceCard(service = service)
            }
        }
    }
}

@Composable
fun ServiceCard(service: HealthService) {
    // State to manage expanded/compressed status of the card
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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