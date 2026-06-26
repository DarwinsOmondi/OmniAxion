package com.example.omniaxion.domain.usecase

import com.example.omniaxion.domain.repository.AuthRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.sendOtp(email)
    }
}
