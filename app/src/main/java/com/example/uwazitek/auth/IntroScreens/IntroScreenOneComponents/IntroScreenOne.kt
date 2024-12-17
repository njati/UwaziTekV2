import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign

@Composable
fun IntroScreenOne(navController: NavController) {
    val images = listOf(
        R.drawable.screenoneimageone,
        R.drawable.screenoneimagetwo,
        R.drawable.screenoneimagethree
    )
    val pagerState = rememberPagerState(pageCount = { images.size }, initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Dimmed Background Images
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.3f), // Dim background
                contentScale = ContentScale.Crop
            )
        }

        // Center Text with Semi-Transparent Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            Text(
                text = when (pagerState.currentPage) {
                    0 -> "Welcome to UwaziTek"
                    1 -> "Your Go-to Destination for the Top-rated Health Insurance Plans"
                    else -> "Explore Our Services"
                },
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Progress Bar
        LinearProgressIndicator(
            progress = (pagerState.currentPage + 1).toFloat() / images.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.TopCenter),
            color = Color.White,
            trackColor = Color.Gray
        )

        // Next Button
        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < images.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        navController.navigate("introscreentwo")
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(0.66f)
        ) {
            Text(text = "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntroScreenOne() {
    val navController = rememberNavController()
    IntroScreenOne(navController)
}
