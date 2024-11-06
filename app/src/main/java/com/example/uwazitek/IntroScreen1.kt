import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uwazitek.R

@Composable
fun IntroScreen1(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Section (2/3 of the screen)
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Centered Image
            Image(
                painter = painterResource(id = R.drawable.firstsplashscreenimageone), // Replace with your image
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main Text
            Text(
                text = "The Go-To Destination for Managing Health Insurance Plans",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Terms and Conditions Text
            Text(
                text = "By proceeding, you agree to the terms and conditions",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = "terms and conditions",
                color = Color.Gray,
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { /* Action on terms click */ }
            )
        }

        // Bottom Section (1/3 of the screen) with Buttons
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Get Started Button
            Button(
                onClick = { /* Action for Get Started */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Get Started")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = { /* Action for Login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFADD8E6), // Light blue background
                    contentColor = Color.Black
                )
            ) {
                Text("Login")
            }
        }
    }
}
