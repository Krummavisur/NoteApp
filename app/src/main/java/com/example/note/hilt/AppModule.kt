package com.example.note.hilt

import android.content.Context
import androidx.room.Room
import com.example.note.data.local.NotesAppDatabase
import com.example.note.data.local.NotesDao
import com.example.note.data.repository.LocalNotesRepository
import com.example.note.data.repository.NotesRepository
import com.example.note.data.local.MIGRATION_1_2
import com.example.note.data.local.MIGRATION_2_3
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesAppDatabase {
        return Room.databaseBuilder(
            context,
            NotesAppDatabase::class.java,
            "note_db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideNoteDao(db: NotesAppDatabase): NotesDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNotesRepository(
        dao: NotesDao,
    ): NotesRepository {
        return LocalNotesRepository(dao)
    }
}