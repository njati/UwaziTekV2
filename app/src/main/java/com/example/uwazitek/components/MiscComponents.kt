package com.example.uwazitek.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Composable functions for each popup content
@Composable
fun PasswordUpdateContent() {
    var newPassword by remember { mutableStateOf("") }
    Column {
        Text(    text = "Update Password",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Handle password update */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))
        ) {
            Text("Update Password")
        }
    }
}

@Composable
fun EmailUpdateContent() {
    var newEmail by remember { mutableStateOf("") }
    Column {
        Text(    text = "Update Email",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        TextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            label = { Text("New Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            label = { Text("Confirm Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Handle email update */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))
        ) {
            Text("Update Email")
        }
    }
}

@Composable
fun PhoneUpdateContent() {
    var newPhone by remember { mutableStateOf("") }
    Column {
        Text(    text = "Update Phone Number",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newPhone,
            onValueChange = { newPhone = it },
            label = { Text("New Phone Number") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newPhone,
            onValueChange = { newPhone = it },
            label = { Text("Confirm Phone Number") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Handle phone update */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6495ED))) {
            Text("Update Phone")
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

@Preview(showBackground = true)
@Composable
fun PreviewMiscComponents() {
    Column {
        PasswordUpdateContent()
        Spacer(modifier = Modifier.height(16.dp))
        EmailUpdateContent()
        Spacer(modifier = Modifier.height(16.dp))
        PhoneUpdateContent()
        Spacer(modifier = Modifier.height(16.dp))
    }
}
