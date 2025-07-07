package com.example.note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.note.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNoteToFavorites(note: NoteEntity)

    @Delete
    suspend fun deleteNoteFromFavorites(noteDao: NoteEntity)

    @Query("SELECT * FROM note")
    fun getAllFavoriteNotes(): Flow<List<NoteEntity>>


    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity?

    @Query("SELECT * FROM note ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>
}