package com.example.uwazitek

import ClaimResponse
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uwazitek.api.APIService
import com.example.uwazitek.auth.tokenManager.TokenManage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ClaimDetailItem(val description: String, val amount: Double, val color: Color)

@Composable
fun ClaimDetailsScreen(
    title: String,
    claims: List<ClaimDetailItem> = emptyList(),
    bottomBar: @Composable () -> Unit
) {
    val context = LocalContext.current
    val token = TokenManager(context).getToken()

    var apiClaims by remember { mutableStateOf<List<ClaimResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch claims only for "All Claims"
    LaunchedEffect(title) {
        if (title == "all") {
            val service = APIService.getAuthService(context)
            service.getClaims().enqueue(object : Callback<List<ClaimResponse>> {
                override fun onResponse(call: Call<List<ClaimResponse>>, response: Response<List<ClaimResponse>>) {
                    if (response.isSuccessful) {
                        apiClaims = response.body() ?: emptyList()
                        Log.d("API Response", "Claims fetched successfully")
                    } else {
                        errorMessage = "Error: ${response.code()} - ${response.errorBody()?.string()}"
                        Log.e("API Response", "Error fetching claims: ${response.errorBody()?.string()}")
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<List<ClaimResponse>>, t: Throwable) {
                    errorMessage = "Failed: ${t.message}"
                    Log.e("API Error", "Request failed: ${t.message}")
                    isLoading = false
                }
            })
        } else {
            isLoading = false // Skip API call for other categories
        }
    }
    // Static placeholder data for other categories
    val placeholderClaims = when (title) {
        "pending" -> listOf(
            ClaimDetailItem("Pending Claim 1", 120.0, Color(0xFFFFC107)),
            ClaimDetailItem("Pending Claim 2", 75.0, Color(0xFFFFC107))
        )
        "approved" -> listOf(
            ClaimDetailItem("Approved Claim 1", 320.0, Color(0xFF4CAF50)),
            ClaimDetailItem("Approved Claim 2", 450.0, Color(0xFF4CAF50))
        )
        "rejected" -> listOf(
            ClaimDetailItem("Rejected Claim 1", 50.0, Color(0xFFF44336)),
            ClaimDetailItem("Rejected Claim 2", 120.0, Color(0xFFF44336))
        )
        else -> emptyList()
    }

    val displayClaims = if (title == "all") {
        apiClaims.map {
            ClaimDetailItem(
                description = it.claimNarration,
                amount = it.invoiceAmount.toDoubleOrNull() ?: 0.0,
                color = Color(0xFF4CAF50) // Default color for API claims
            )
        }
    } else {
        placeholderClaims
    }

    Scaffold(bottomBar = { bottomBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color(0xFF2D3436),
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Hi, User 👋",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3436),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF2D3436),
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = title.replaceFirstChar { it.uppercase() } + " Claims",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3436)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loading or Error
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "An error occurred.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                // Determine whether to show API data or placeholder claims
                val displayClaims = if (title == "all") {
                    apiClaims.map {
                        ClaimDetailItem(
                            description = it.claimNarration,
                            amount = it.invoiceAmount.toDoubleOrNull() ?: 0.0,
                            color = Color(0xFF4CAF50)
                        )
                    }
                } else {
                    claims // Use placeholder claims for other types
                }

                // Claims List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(displayClaims) { claim ->
                        ClaimRow(claim)
                    }
                }

                // Total Amount
                val totalCharges = displayClaims.sumOf { it.amount }
                Text(
                    text = "Total: Ksh ${"%.2f".format(totalCharges)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3436),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun ClaimRow(claim: ClaimDetailItem) {
    var isHighlighted by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHighlighted) claim.color else Color(0xFFEFEFEF)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { isHighlighted = !isHighlighted },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = claim.description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2D3436)
        )
        Text(
            text = "Ksh ${"%.2f".format(claim.amount)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3436)
        )
    }
}



fun getClaims(claimType: String): List<ClaimDetailItem> {
    return when (claimType) {
        "pending" -> listOf(
            ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
            ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
            ClaimDetailItem("Charge C", 180.0, Color(0xFFFFC107))
        )

        "approved" -> listOf(
            ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
            ClaimDetailItem("Charge Y", 450.0, Color(0xFF4CAF50)),
            ClaimDetailItem("Charge Z", 230.0, Color(0xFF4CAF50))
        )

        "rejected" -> listOf(
            ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336)),
            ClaimDetailItem("Charge N", 120.0, Color(0xFFF44336)),
            ClaimDetailItem("Charge O", 30.0, Color(0xFFF44336))
        )

        else -> listOf(
            ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
            ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
            ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
            ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336))
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewClaimDetailsScreen() {
//    val claims = listOf(
//        ClaimDetailItem("Charge A", 120.0, Color(0xFFFFC107)),
//        ClaimDetailItem("Charge B", 75.0, Color(0xFFFFC107)),
//        ClaimDetailItem("Charge X", 320.0, Color(0xFF4CAF50)),
//        ClaimDetailItem("Charge M", 50.0, Color(0xFFF44336))
//    )
//    ClaimDetailsScreen(
//        "All Claims", claims,
//        bottomBar =  { BottomNavigationBar(navController) }
//    )
//}