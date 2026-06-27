package com.example.omniaxion.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.ui.theme.OmniAxisColors

import androidx.compose.ui.tooling.preview.Preview
import com.example.omniaxion.ui.theme.OmniAxionTheme

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    AuthContent(
        state = state,
        onSendOtp = viewModel::sendOtp,
        onVerifyOtp = viewModel::verifyOtp,
        onAuthSuccess = onAuthSuccess
    )
}

@Composable
fun AuthContent(
    state: AuthState,
    onSendOtp: (String) -> Unit,
    onVerifyOtp: (String) -> Unit,
    onAuthSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onAuthSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "OMNIAXIS.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = OmniAxisColors.PrimaryBlue
        )
        Text(
            text = "The news intelligence console",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        if (state.step == AuthStep.EMAIL) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onSendOtp(email) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OmniAxisColors.PrimaryBlue),
                shape = MaterialTheme.shapes.extraSmall,
                enabled = !state.isLoading && email.isNotBlank()
            ) {
                if (state.isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("SIGN IN / UP")
            }
        } else {
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Enter verification code") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onVerifyOtp(code) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OmniAxisColors.PrimaryBlue),
                shape = MaterialTheme.shapes.extraSmall,
                enabled = !state.isLoading && code.isNotBlank()
            ) {
                if (state.isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("VERIFY")
            }
        }

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Text(
            text = "or continue with",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Google", color = OmniAxisColors.PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text("Passkey", color = OmniAxisColors.PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenEmailPreview() {
    OmniAxionTheme {
        AuthContent(
            state = AuthState(step = AuthStep.EMAIL),
            onSendOtp = {},
            onVerifyOtp = {},
            onAuthSuccess = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenVerificationPreview() {
    OmniAxionTheme {
        AuthContent(
            state = AuthState(step = AuthStep.VERIFICATION, email = "user@example.com"),
            onSendOtp = {},
            onVerifyOtp = {},
            onAuthSuccess = {}
        )
    }
}

