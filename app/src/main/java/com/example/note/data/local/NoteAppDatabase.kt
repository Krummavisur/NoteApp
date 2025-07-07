package com.example.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.note.data.local.NoteDao
import com.example.note.data.local.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteAppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}