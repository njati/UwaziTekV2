package com.example.uwazitek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthinsuranceapp.ui.theme.HealthInsuranceAppTheme

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
    NavHost(navController = navController, startDestination = "intro1") {
        composable("intro1") { IntroScreen1(navController) }
        composable("intro2") { IntroScreen2(navController) }
        composable("login") { LoginScreen() }
    }
}

@Composable
fun IntroScreen1(navController: NavHostController) {
    Surface {
        // UI for IntroScreen1
    }
}

@Composable
fun IntroScreen2(navController: NavHostController) {
    Surface {
        // UI for IntroScreen2
    }
}

@Composable
fun LoginScreen() {
    Surface {
        //UI for LoginScreen
    }
}}
