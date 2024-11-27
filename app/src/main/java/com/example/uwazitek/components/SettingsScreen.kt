package com.example.uwazitek.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uwazitek.R
import androidx.compose.ui.platform.LocalContext
import com.example.uwazitek.auth.tokenManager.TokenManager
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Updated Settings screen function
    @Composable
    fun SettingsScreen(navController: NavController, paddingValues: PaddingValues) {
        var showContactSupportPopup by remember { mutableStateOf(false) }
        var showUpdatePasswordPopup by remember { mutableStateOf(false) }
        var showUpdateEmailPopup by remember { mutableStateOf(false) }
        var showUpdatePhonePopup by remember { mutableStateOf(false) }
        var showChangeProfilePicPopup by remember { mutableStateOf(false) }
        var showChatButton by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        var response by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center elements vertically
        )
        {
            // Profile Picture at the Top Center
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { showChangeProfilePicPopup = true },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.firstsplashscreenimageone),                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Contact Support with Confirmation Field
            Button(onClick = { showContactSupportPopup = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
                Text("Contact Support")
            }
            if (showContactSupportPopup) {
                PopupDialog(
                    onDismiss = { showContactSupportPopup = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )

                        {
                            Text("Chat with Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = "",
                                onValueChange = {},
                                placeholder = { Text("Type your message...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { /* Handle send message */ }) {
                                Text("Send")
                            }
                        }

                    }
                )
                showChatButton = true
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Update Password with Confirmation Field
            Button(onClick = { showUpdatePasswordPopup = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
                Text("Update Password")
            }
            if (showUpdatePasswordPopup) {
                PopupDialog(
                    onDismiss = { showUpdatePasswordPopup = false },
                    content = { PasswordUpdateContent() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Update Email with Confirmation Field
            Button(onClick = { showUpdateEmailPopup = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
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
            Button(onClick = { showUpdatePhonePopup = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
                Text("Update Phone Number")
            }
            if (showUpdatePhonePopup) {
                PopupDialog(
                    onDismiss = { showUpdatePhonePopup = false },
                    content = { PhoneUpdateContent() } // Adds confirmation field
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            Button(onClick = {
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0C0C0))) {
                Text("Logout")
            }
        }
        }

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    // Provide a mock NavController for preview
    val navController = rememberNavController()
    SettingsScreen(navController = navController, paddingValues = PaddingValues())
}