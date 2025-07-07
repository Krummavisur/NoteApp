package com.example.note.data.repository

import com.example.note.domain.DecryptedNote
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllFavoriteNotes(): Flow<List<DecryptedNote>>
    fun getAllNotes(): Flow<List<DecryptedNote>>
    suspend fun getNoteById(id: Int): DecryptedNote?
    suspend fun addOrUpdateNote(title: String, content: String, isFavorite: Boolean = false)
    suspend fun deleteNote(noteId: Int)
}
