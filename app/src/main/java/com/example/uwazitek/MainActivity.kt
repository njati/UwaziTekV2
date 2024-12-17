package com.example.uwazitek

//import ClaimsDashboard
import IntroScreenOne
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.auth.IntroScreens.IntroScreenOneComponents.SplashScreen
import com.example.uwazitek.auth.IntroScreens.IntroScreenTwoComponents.IntroScreenTwo
import com.example.uwazitek.ui.theme.HealthInsuranceAppTheme
import com.example.uwazitek.auth.login.LoginScreen
import com.example.uwazitek.auth.signup.SignUpScreen
import com.example.uwazitek.components.ClaimsDashboard
import com.example.uwazitek.components.ServicesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthInsuranceAppTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    @Composable
    fun SetupNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "splashscreen") {
                composable("introscreenone") { IntroScreenOne(navController) }
                composable("introscreentwo") { IntroScreenTwo(navController) }
                composable("login") {LoginScreen(navController)}
                composable("dashboard") {ClaimsDashboard(
                    navController,
                    username = "John Doe"
                ) }
                composable("signup") { SignUpScreen(navController) }
                composable("servicesscreen") { ServicesScreen(navController) }
                composable("splashscreen") { SplashScreen(navController) }
            }
        }
    }
