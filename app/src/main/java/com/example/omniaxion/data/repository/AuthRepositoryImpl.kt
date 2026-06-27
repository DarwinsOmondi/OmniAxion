package com.example.omniaxion.data.repository

import com.example.omniaxion.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override suspend fun sendOtp(email: String): Result<Unit> {
        return try {
            Timber.d("Sending OTP to $email")
            supabaseClient.auth.signInWith(OTP) {
                this.email = email
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error sending OTP")
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(email: String, token: String): Result<Unit> {
        return try {
            Timber.d("Verifying OTP for $email")
            supabaseClient.auth.verifyEmailOtp(
                type = OtpType.Email.EMAIL,
                email = email,
                token = token
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error verifying OTP")
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            Timber.d("Signing out")
            supabaseClient.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error signing out")
            Result.failure(e)
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return supabaseClient.auth.currentSessionOrNull() != null
    }
}
