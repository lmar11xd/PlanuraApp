package com.lmar.planuraapp.domain.repository.common

import android.net.Uri
import com.lmar.planuraapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun listenForUpdates(userId: String): Flow<User>
    suspend fun createUser(user: User): Boolean
    suspend fun getUserById(userId: String): User?
    suspend fun uploadProfileImage(userId: String, uri: Uri): String?
    suspend fun updateUser(user: User): Boolean
}