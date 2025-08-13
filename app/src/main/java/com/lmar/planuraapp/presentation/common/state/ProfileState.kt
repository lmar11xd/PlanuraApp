package com.lmar.planuraapp.presentation.common.state

import android.net.Uri
import com.lmar.planuraapp.domain.model.User

data class ProfileState (
    val user: User = User(),
    val imageUri: Uri? = null,
    val isShowingForm: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
)