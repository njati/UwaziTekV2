package com.example.uwazitek.auth.tokenManager

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_ISSUE_TIME = "issue_time"
        private const val TOKEN_EXPIRY_TIME = 24 * 60 * 60 * 1000 // 24 hours in milliseconds
    }

    // Save the token and the issue time
    fun saveToken(token: String) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putLong(KEY_ISSUE_TIME, System.currentTimeMillis())
            apply()
        }
    }

    // Get the token if it hasn’t expired
    fun getToken(): String? {
        val token = prefs.getString(KEY_TOKEN, null)
        val issueTime = prefs.getLong(KEY_ISSUE_TIME, 0)

        return if (System.currentTimeMillis() - issueTime < TOKEN_EXPIRY_TIME) token else null
    }

    // Clear the token (e.g., on logout)
    fun clearToken() {
        prefs.edit().apply {
            remove(KEY_TOKEN)
            remove(KEY_ISSUE_TIME)
            apply()
        }
    }
}