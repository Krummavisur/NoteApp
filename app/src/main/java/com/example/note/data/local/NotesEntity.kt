package com.example.note.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NotesEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val encryptedContent: String,
    val timestamp: Long,
    val isFavorite: Boolean = false
)