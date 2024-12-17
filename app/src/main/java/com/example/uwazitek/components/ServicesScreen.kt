package com.example.uwazitek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uwazitek.api.dto.HealthService

@Composable
fun ServicesScreen(paddingValues: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    // Sample data for services
    val services = listOf(
        HealthService("Dentist", "Kenyatta National Hospital", "Nairobi", "8 AM - 6 PM", "$100", "0712-345-678", 4.2f),
        HealthService("Cardiologist", "Aga Khan University Hospital", "Nairobi", "9 AM - 5 PM", "$300", "0722-123-456", 4.8f),
        HealthService("Pediatrician", "Nairobi Hospital", "Nairobi", "10 AM - 4 PM", "$150", "0733-987-654", 4.5f)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Services", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        )

        // LazyColumn for services
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            items(services.filter {
                it.name.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
            }) { service ->
                ServiceCard(service = service)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServicesScreen() {
    val navController = rememberNavController()
    ServicesScreen(navController)
}



