package com.example.nextwaveconnect.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    fun signIn(email: String, password: String) : Task<AuthResult>
    fun signUp(email: String, password: String) : Task<AuthResult>
}