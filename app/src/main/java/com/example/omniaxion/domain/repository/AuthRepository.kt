package com.example.omniaxion.domain.repository

interface AuthRepository {
    suspend fun sendOtp(email: String): Result<Unit>
    suspend fun verifyOtp(email: String, token: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun isUserLoggedIn(): Boolean
}
