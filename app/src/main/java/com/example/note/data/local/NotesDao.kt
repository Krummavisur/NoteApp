package com.example.note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNoteToFavorites(note: NotesEntity)

    @Delete
    suspend fun deleteNoteFromFavorites(noteDao: NotesEntity)

    @Query("SELECT * FROM note")
    fun getAllFavoriteNotes(): Flow<List<NotesEntity>>


    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NotesEntity?

    @Query("SELECT * FROM note ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NotesEntity>>
}