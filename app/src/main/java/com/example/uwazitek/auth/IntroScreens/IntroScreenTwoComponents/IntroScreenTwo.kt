package com.example.uwazitek.auth.IntroScreens.IntroScreenTwoComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.R
import kotlinx.coroutines.delay

@Composable
fun IntroScreenTwo(navController: NavHostController) {
    val items = listOf(
        "Locate Hospital" to Icons.Default.Place,
        "Login" to Icons.Default.Login,
        "Sign Up" to Icons.Default.PersonAdd,
        "BMI Calculator" to Icons.Default.Calculate,
        "Explore Services" to Icons.Default.Explore,
        "Forgot Password" to Icons.Default.LockOpen
    )
    val colors = listOf(
        Color(0xFF3A72FF),
        Color(0xFFFFA726),
        Color(0xFF66BB6A),
        Color(0xFFEF5350),
        Color(0xFFAB47BC),
        Color(0xFF29B6F6)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Blurred Background Image Carousel
        BlurredImageCarousel()

        // Centered Cards
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(16.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items.zip(colors)) { (item, color) ->
                    val (title, icon) = item
                    ClickableCardWithIcon(
                        title = title,
                        icon = { Icon(icon, contentDescription = null, tint = Color.White) },
                        color = color
                    ) {
                        // Navigate to the corresponding route
                        when (title) {
                            "Login" -> navController.navigate("login")
                            "Sign Up" -> navController.navigate("signup")
                            "Locate Hospital" -> navController.navigate("locate_hospital")
                            "BMI Calculator" -> navController.navigate("bmi_calculator")
                            "Explore Services" -> navController.navigate("explore_services")
                            "Forgot Password" -> navController.navigate("forgot_password")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlurredImageCarousel() {
    val images = listOf(
        R.drawable.screenoneimageone,
        R.drawable.screenoneimagetwo,
        R.drawable.screenoneimagethree
    )
    var currentImageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentImageIndex = (currentImageIndex + 1) % images.size
        }
    }

    Image(
        painter = painterResource(id = images[currentImageIndex]),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ClickableCardWithIcon(
    title: String,
    icon: @Composable () -> Unit,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntroScreenTwo() {
    val navController = rememberNavController()
    IntroScreenTwo(navController)
}