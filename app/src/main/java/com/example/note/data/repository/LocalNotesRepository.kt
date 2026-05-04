package com.example.note.data.repository

import com.example.note.data.local.NotesDao
import com.example.note.data.local.NotesEntity
import com.example.note.domain.Note
import com.example.note.mapper.toMappedNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalNotesRepository(
    private val notesDao: NotesDao,
) : NotesRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        notesDao.getAllActiveNotes().map { list ->
            list.mapNotNull { it.toMappedNote() }
        }

    override fun getAllFinishedNotes(): Flow<List<Note>> =
        notesDao.getAllFinishedNotes().map { list ->
            list.mapNotNull { it.toMappedNote() }
        }

    override suspend fun getNoteById(id: Int): Note? {
        return notesDao.getNoteById(id)?.toMappedNote()
    }

    override suspend fun addNote(title: String, content: String, isFinished: Boolean) {
        val note = NotesEntity(
            title = title,
            content = content,
            timestamp = System.currentTimeMillis(),
            isFinished = isFinished
        )
        notesDao.insertNote(note)
    }

    override suspend fun deleteNote(noteId: Int) {
        val note = notesDao.getNoteById(noteId)
        note?.let { notesDao.deleteNote(it) }
    }

    override suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        isFinished: Boolean
    ) {
        val existingNote = notesDao.getNoteById(noteId) ?: return

        val updatedNote = existingNote.copy(
            title = title,
            content = content,
            isFinished = isFinished,
            timestamp = System.currentTimeMillis()
        )

        notesDao.insertNote(updatedNote)
    }

    override suspend fun toggleFinished(noteId: Int) {
        val note = notesDao.getNoteById(noteId) ?: return
        val finishedNote = note.copy(isFinished = !note.isFinished)
        notesDao.insertNote(finishedNote)
    }
}