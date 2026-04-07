package com.example.note.data.repository

import com.example.note.domain.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllFinishedNotes(): Flow<List<Note>>
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun addNote(title: String, content: String, isFavorite: Boolean = false)
    suspend fun deleteNote(noteId: Int)
    suspend fun toggleFavorite(noteId: Int)
    suspend fun addToFinished(noteId: Int)
    suspend fun updateNote(noteId: Int, title: String, content: String, isFavorite: Boolean)
}
