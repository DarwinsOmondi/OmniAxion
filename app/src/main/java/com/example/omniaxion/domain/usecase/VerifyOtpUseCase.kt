package com.example.omniaxion.domain.usecase

import com.example.omniaxion.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, token: String): Result<Unit> {
        return repository.verifyOtp(email, token)
    }
}
