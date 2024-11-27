package com.example.uwazitek.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.uwazitek.R
import androidx.compose.animation.animateContentSize
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        contentColor = Color.White,
        backgroundColor = Color(0xFF6495ED)
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pending Claims") },
            selected = navController.currentDestination?.route == "pending_claims",
            selectedContentColor = Color(0xFF00008B), // Dark blue color for selected item
            unselectedContentColor = Color.White,
            onClick = { navController.navigate("pending_claims") },
            alwaysShowLabel = false,
            modifier = Modifier.animateContentSize()
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Approved Claims") },
            selected = navController.currentDestination?.route == "approved_claims",
            selectedContentColor = Color(0xFF00008B), // Dark blue color for selected item
            unselectedContentColor = Color.White,
            onClick = { navController.navigate("approved_claims") },
            alwaysShowLabel = false,
            modifier = Modifier.animateContentSize()
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.baseline_cancel_24), contentDescription = "Rejected Claims") },
            selected = navController.currentDestination?.route == "rejected_claims",
            selectedContentColor = Color(0xFF00008B), // Dark blue color for selected item
            unselectedContentColor = Color.White,
            onClick = { navController.navigate("rejected_claims") },
            alwaysShowLabel = false,
            modifier = Modifier.animateContentSize()
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = navController.currentDestination?.route == "settings",
            selectedContentColor = Color(0xFFFFA500), // Orange color for selected item
            unselectedContentColor = Color.White,
            onClick = { navController.navigate("settings") },
            alwaysShowLabel = false,
            modifier = Modifier.animateContentSize()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(navController = NavController(LocalContext.current))
}
