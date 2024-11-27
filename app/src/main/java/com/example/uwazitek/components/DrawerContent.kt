package com.example.uwazitek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DrawerContent(navController: NavController, onCloseDrawer: () -> Unit) {
    var showMenuItems by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.5f)
            .background(Color(0xFF6495ED))
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
        Button(onClick = { showMenuItems = !showMenuItems },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
            Text("View Claims")
        }
        if (showMenuItems) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
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
                    text = "Rejected",
                    navController = navController,
                    route = "rejected_claims",
                    onCloseDrawer = onCloseDrawer
                )
            }
        }
        // Extra menu items
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
@Preview(showBackground = true)
@Composable
fun PreviewDrawerContent() {
    DrawerContent(navController = NavController(LocalContext.current), onCloseDrawer = {})
}