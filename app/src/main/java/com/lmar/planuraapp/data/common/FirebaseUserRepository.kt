package com.lmar.planuraapp.data.common

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.lmar.planuraapp.core.utils.Constants
import com.lmar.planuraapp.domain.model.User
import com.lmar.planuraapp.domain.repository.common.IUserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    private val database: DatabaseReference,
    private val storage: FirebaseStorage
) : IUserRepository {

    companion object {
        private const val TAG = "FirebaseUserRepository"
    }

    override suspend fun createUser(user: User): Boolean {
        return try {
            database.child(user.id).setValue(user).await()
            Log.d(TAG, "Usuario creado con éxito: ${user.id}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al crear usuario ${user.id}", e)
            false
        }
    }

    override suspend fun getUserById(userId: String): User? {
        if (userId.isBlank()) return null

        return try {
            val snapshot = database.child(userId).get().await()
            snapshot.getValue(User::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener usuario: ${e.message}", e)
            null
        }
    }

    override fun listenForUpdates(userId: String): Flow<User> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(User::class.java)?.let { trySend(it).isSuccess }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        val ref = database.child(userId)
        ref.addValueEventListener(listener)

        awaitClose { ref.removeEventListener(listener) }
    }

    override suspend fun uploadProfileImage(userId: String, uri: Uri): String? {
        return try {
            val ref = storage.getReference("${Constants.STORAGE_REFERENCE}/$userId.jpg")
            ref.putFile(uri).await()
            val downloadUrl = ref.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            Log.e(TAG, "Error al subir imagen de perfil de $userId", e)
            null
        }
    }

    override suspend fun updateUser(user: User ): Boolean {
        return try {
            database.child(user.id).setValue(user).await()
            Log.d(TAG, "Usuario actualizado: ${user.id}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar usuario ${user.id}", e)
            false
        }
    }
}