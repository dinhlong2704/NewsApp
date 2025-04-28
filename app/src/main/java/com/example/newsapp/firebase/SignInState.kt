package com.example.newsapp.firebase


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
