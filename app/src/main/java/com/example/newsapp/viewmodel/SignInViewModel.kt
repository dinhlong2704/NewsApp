package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.firebase.SignInResult


class SignInViewModel : ViewModel() {
    private val _state = MutableLiveData(SignInState())
    val state: LiveData<SignInState> = _state

    fun onSignInResult(result: SignInResult) {
        _state.value = SignInState(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
    }

    fun resetState() {
        _state.value = SignInState()
    }
}

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)