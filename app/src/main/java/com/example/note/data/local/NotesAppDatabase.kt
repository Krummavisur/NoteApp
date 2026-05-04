package com.example.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [NotesEntity::class], version = 2)
abstract class NotesAppDatabase: RoomDatabase() {
    abstract fun noteDao(): NotesDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE note RENAME COLUMN isFavorite TO isFinished")
    }
}