package com.example.omniaxion.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omniaxion.domain.usecase.SendOtpUseCase
import com.example.omniaxion.domain.usecase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun sendOtp(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            sendOtpUseCase(email)
                .onSuccess {
                    Timber.d("OTP sent successfully to $email")
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            email = email,
                            step = AuthStep.VERIFICATION 
                        ) 
                    }
                }
                .onFailure { error ->
                    Timber.e(error, "Failed to send OTP")
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            error = error.message ?: "Failed to send OTP" 
                        ) 
                    }
                }
        }
    }

    fun verifyOtp(token: String) {
        val email = _uiState.value.email ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            verifyOtpUseCase(email, token)
                .onSuccess {
                    Timber.d("OTP verified successfully")
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            isLoggedIn = true 
                        ) 
                    }
                }
                .onFailure { error ->
                    Timber.e(error, "Failed to verify OTP")
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            error = error.message ?: "Invalid OTP" 
                        ) 
                    }
                }
        }
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val step: AuthStep = AuthStep.EMAIL,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)

enum class AuthStep {
    EMAIL, VERIFICATION
}
