package com.example.uwazitek.auth.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.uwazitek.components.LoginTextField
import com.example.uwazitek.ui.theme.HealthInsuranceAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.R
import com.example.uwazitek.api.APIService
import com.example.uwazitek.api.dto.Login
import com.example.uwazitek.auth.tokenManager.TokenManager
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import retrofit2.Callback
import retrofit2.Response

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    // State for user input fields
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (checked, onCheckedChange) = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(defaultPadding)
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.firstsplashscreenimageone),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.2f) // Faded effect
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = "Login",
                modifier = Modifier.padding(bottom = itemSpacing * 2),
                style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center, color = Color.Gray)
            )
            LoginTextField(
                value = email,
                onValueChange = setEmail,
                labelText = "Enter Your Email",
                modifier = Modifier.fillMaxWidth(),
                // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(itemSpacing))

            // Password Field
            LoginTextField(
                value = password,
                onValueChange = setPassword,
                labelText = "Password",
                modifier = Modifier.fillMaxWidth(),
                // visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(itemSpacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Text(
                        text = "Remember Me",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Text(
                    text = "Forgot your password?",
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { /* Forgot Password action */ }
                )
            }

            Spacer(Modifier.height(itemSpacing))

            // Login Button
            Button(
                onClick = {
                    isLoading = true
                    performLogin(email, password, context, navController) {
                        isLoading = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultPadding),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "Logging In..." else "Login",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                )
            }


            Spacer(Modifier.height(itemSpacing))

            Row(
                modifier = Modifier.padding(top = itemSpacing * 2),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an account?")
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { navController.navigate("signup") }
                )
            }

            Spacer(Modifier.weight(1f))
        }
    }
}

// Login Function
private fun performLogin(
    email: String,
    password: String,
    context: Context,
    navController: NavController,
    onComplete: () -> Unit
) {
    val loginRequest = Login(email, password)
    val authService = APIService.getAuthService(context)

    authService.login(loginRequest).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            onComplete()
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                val jsonResponse = JSONObject(responseBody ?: "{}")
                val accessToken = jsonResponse.optString("access_token", "")

                if (accessToken.isNotEmpty()) {
                    TokenManager(context).saveToken(accessToken)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("dashboard")
                } else {
                    Toast.makeText(context, "Login failed: No token received", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            onComplete()
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}
fun showNotRegisteredUserMessage(context: Context) {
    Log.d("Uwazitek", "You're not a registered Policy Holder")
    // You can also show a Snackbar or Toast here if needed
    Toast.makeText(context, "You're not a registered Policy Holder", Toast.LENGTH_SHORT).show()
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    HealthInsuranceAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}