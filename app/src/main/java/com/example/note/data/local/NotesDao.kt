package com.example.note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesEntity)

    @Delete
    suspend fun deleteNote(note: NotesEntity)

    @Query("SELECT * FROM note WHERE isFinished = 1")
    fun getAllFinishedNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM note WHERE isFinished = 0")
    fun getAllActiveNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NotesEntity?
}