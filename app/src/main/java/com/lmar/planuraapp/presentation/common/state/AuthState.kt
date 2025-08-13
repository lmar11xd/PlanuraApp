package com.lmar.planuraapp.presentation.common.state

import com.lmar.planuraapp.domain.model.User

data class AuthState(
    val user: User = User(),
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val names: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
)