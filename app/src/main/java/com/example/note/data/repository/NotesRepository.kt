package com.example.note.data.repository

import com.example.note.domain.DecryptedNotes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllFavoriteNotes(): Flow<List<DecryptedNotes>>
    fun getAllNotes(): Flow<List<DecryptedNotes>>
    suspend fun getNoteById(id: Int): DecryptedNotes?
    suspend fun addOrUpdateNote(title: String, content: String, isFavorite: Boolean = false)
    suspend fun deleteNote(noteId: Int)
}
