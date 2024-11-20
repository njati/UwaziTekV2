package com.example.uwazitek.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.uwazitek.components.LoginTextField
import com.example.healthinsuranceapp.ui.theme.HealthInsuranceAppTheme
import androidx.compose.foundation.clickable
import com.example.uwazitek.R

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    // State for user input fields
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (checked, onCheckedChange) = rememberSaveable { mutableStateOf(false) }

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
                labelText = "Email",
                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(itemSpacing))

            // Password Field
            LoginTextField(
                value = password,
                onValueChange = setPassword,
                labelText = "Password",
                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation()
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
                onClick = { /* login logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultPadding),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Login",
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
                    modifier = Modifier.clickable { /* Sign Up action */ }
                )
            }

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    HealthInsuranceAppTheme {
        LoginScreen()
    }
}
