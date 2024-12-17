package com.example.uwazitek.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define custom color schemes for light and dark themes
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0D47A1),     // Dark Blue
    onPrimary = Color.Black,          // Black for text/icons on primary
    secondary = Color(0xFF1976D2),    // Medium Blue
    tertiary = Color(0xFFBBDEFB),     // Light Blue
    background = Color(0xFF1565C0),   // Another shade of Blue for background
    surface = Color(0xFF0D47A1),      // Dark Blue as a surface color
    onBackground = Color(0xFFC0C0C0),  // Light Grey on background
    onSurface = Color.Black           // Black on surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),      // Medium Blue
    onPrimary = Color.Black,          // Black for text/icons on primary
    secondary = Color(0xFF2196F3),    // Lighter Blue
    tertiary = Color(0xFFBBDEFB),     // Very Light Blue
    background = Color(0xFFE3F2FD),   // Light Blue Background
    surface = Color(0xFF1976D2),      // Medium Blue for surfaces
    onBackground = Color(0xFFC0C0C0),  // Light Grey on background
    onSurface = Color.Black           // Black on surface
)


// Shape settings, customize as needed
val Shapes = Shapes(
    // Define your shapes here
)

@Composable
fun HealthInsuranceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
