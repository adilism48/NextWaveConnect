package com.example.nextwaveconnect.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUpUseCase(private val authRepository: AuthRepository) {
    fun signUp(email: String, password: String) : Task<AuthResult> {
        return authRepository.signUp(email, password)
    }
}