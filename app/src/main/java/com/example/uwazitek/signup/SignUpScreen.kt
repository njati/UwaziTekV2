

package com.example.uwazitek.createaccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.uwazitek.components.LoginTextField
import com.example.healthinsuranceapp.ui.theme.HealthInsuranceAppTheme
import com.example.uwazitek.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable


val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun CreateAccountScreen(modifier: Modifier = Modifier) {
    // State for user input fields
    val (userName, setUsername) = rememberSaveable { mutableStateOf("") }
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = rememberSaveable { mutableStateOf("") }
    val (checked, onCheckedChange) = rememberSaveable { mutableStateOf(false) }

    // Snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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

        // Foreground Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            // Header Text
            Text(
                text = "Create Account",
                modifier = Modifier.padding(bottom = itemSpacing * 2),
                style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center, color = Color.Gray)
            )

            // Username Field
            LoginTextField(
                value = userName,
                onValueChange = setUsername,
                labelText = "Username",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(itemSpacing))

            // Email Field
            LoginTextField(
                value = email,
                onValueChange = setEmail,
                labelText = "Email",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(itemSpacing))

            // Password Field
            LoginTextField(
                value = password,
                onValueChange = setPassword,
                labelText = "Password",
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(itemSpacing))

            // Confirm Password Field
            LoginTextField(
                value = confirmPassword,
                onValueChange = setConfirmPassword,
                labelText = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(itemSpacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Terms and Conditions Checkbox and Text
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Text(
                        text = "I agree to the Terms and Conditions",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(Modifier.height(itemSpacing))

            // Create Account Button
            Button(
                onClick = {
                    if (!checked) {
                        // Show snackbar if checkbox is not checked
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("You must agree to the Terms and Conditions.")
                        }
                    } else {
                        // Clear the form inputs if checkbox is checked
                        setUsername("")
                        setEmail("")
                        setPassword("")
                        setConfirmPassword("")
                        onCheckedChange(false) // Uncheck the checkbox
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultPadding),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Create Account",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                )
            }

            Spacer(Modifier.height(itemSpacing * 2))

            // Already have an account? Text
            Row(
                modifier = Modifier.padding(top = itemSpacing * 2),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Already have an account?")
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { /* Handle Login action */ }
                )
            }

            Spacer(Modifier.weight(1f))
        }

        // Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevCreateAccountScreen() {
    HealthInsuranceAppTheme {
        CreateAccountScreen()
    }
}
