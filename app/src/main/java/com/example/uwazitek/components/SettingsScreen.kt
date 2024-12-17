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
import androidx.compose.material3.Scaffold
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
fun SettingsScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(),
    bottomBar: @Composable () -> Unit // Accept the BottomNavigationBar as a parameter
) {
    var showContactSupportPopup by remember { mutableStateOf(false) }
    var showUpdatePasswordPopup by remember { mutableStateOf(false) }
    var showUpdateEmailPopup by remember { mutableStateOf(false) }
    var showUpdatePhonePopup by remember { mutableStateOf(false) }
    var showChangeProfilePicPopup by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        bottomBar = { bottomBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Account for bottom bar padding
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { showChangeProfilePicPopup = true },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.firstsplashscreenimageone),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Buttons and Popups
            Button(onClick = { showContactSupportPopup = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Contact Support")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showUpdatePasswordPopup = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Update Password")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showUpdateEmailPopup = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Update Email")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showUpdatePhonePopup = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Update Phone Number")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { TokenManager(context).logout(navController) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0C0C0))
            ) {
                Text("Logout")
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewSettingsScreen() {
//    // Provide a mock NavController for preview
//    val navController = rememberNavController()
//    SettingsScreen(navController = navController, paddingValues = PaddingValues())
//}