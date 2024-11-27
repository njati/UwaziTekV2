package com.example.uwazitek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uwazitek.Claim

@Composable
fun ClaimsOverviewScreen(title: String, claims: List<Claim>, paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("One Month", "Three Months", "Six Months", "One Year", "Three Years")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            // Dropdown menu placeholder
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { expanded = true }
            ) {
                Text(
                    text = selectedOption,
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = option
                                expanded = false
                            },
                            text = { Text(text = option) })
                    }
                }
            }
            // TextView showing total amount of claims
            Text(
                text = "Total: \$0.00",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        // Display the search bar below the title
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Claims", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        )

        // LazyColumn to display filtered claims
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Take up available space
        ) {
            items(claims.filter {
                it.id.contains(searchQuery, ignoreCase = true) || it.amount.contains(searchQuery, ignoreCase = true)
            }) { claim ->
                ClaimsCard(sectionTitle = claim.id, totalAmount = claim.amount)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClaimsOverviewScreen() {
    val sampleClaims = listOf(
        Claim(id = "Claim1", amount = "100"),
        Claim(id = "Claim2", amount = "200"),
        Claim(id = "Claim3", amount = "300")
    )
    ClaimsOverviewScreen(title = "Claims Overview", claims = sampleClaims, paddingValues = PaddingValues(0.dp))
}