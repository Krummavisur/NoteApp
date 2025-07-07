package com.example.note.data.repository

import com.example.note.data.local.NoteDao
import com.example.note.data.local.NoteEntity
import com.example.note.domain.DecryptedNote
import com.example.note.encryption.CryptoManager
import com.example.note.mapper.toDecryptedNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalNotesRepository(
    private val noteDao: NoteDao,
    private val cryptoManager: CryptoManager
) : NotesRepository {

    override fun getAllNotes(): Flow<List<DecryptedNote>> =
        noteDao.getAllNotes().map { list ->
            list.mapNotNull { it.toDecryptedNote(cryptoManager) }
        }

    override fun getAllFavoriteNotes(): Flow<List<DecryptedNote>> =
        noteDao.getAllFavoriteNotes().map { list ->
            list.mapNotNull { it.toDecryptedNote(cryptoManager) }
        }

    override suspend fun getNoteById(id: Int): DecryptedNote? {
        return noteDao.getNoteById(id)?.toDecryptedNote(cryptoManager)
    }

    override suspend fun addOrUpdateNote(title: String, content: String, isFavorite: Boolean) {
        val encrypted = cryptoManager.encryptText(content)
        val note = NoteEntity(
            title = title,
            encryptedContent = encrypted,
            timestamp = System.currentTimeMillis(),
            isFavorite = isFavorite
        )
        noteDao.insertNoteToFavorites(note)
    }

    override suspend fun deleteNote(noteId: Int) {
        val note = noteDao.getNoteById(noteId)
        note?.let { noteDao.deleteNoteFromFavorites(it) }
    }
}