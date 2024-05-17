package com.example.nextwaveconnect.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignInUseCase(private val authRepository: AuthRepository) {
    fun signIn(email: String, password: String) : Task<AuthResult> {
        return authRepository.signIn(email, password)
    }
}