package com.example.uwazitek.auth.IntroScreens.IntroScreenOneComponents

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.R // Replace with your actual package for the drawable resource

@Composable
fun SplashScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    // Simulate loading for 5 seconds
    LaunchedEffect(Unit) {
        delay(5000)
        isLoading = false
        navController.navigate("introscreenone")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Background image with conditional blur
        BlurredBackground(imageRes = R.drawable.screenoneimageone)

        // Loading content
        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun BlurredBackground(imageRes: Int) {
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // Use RenderEffect for API 31+
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Blurred Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    } else {
        // Use a pre-blurred image for lower API levels
        Image(
            painter = painterResource(id = imageRes), // Replace with pre-blurred image if needed
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    val navController = rememberNavController()
    SplashScreen(navController)
}