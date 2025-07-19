package com.example.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesAppDatabase: RoomDatabase() {
    abstract fun noteDao(): NotesDao
}