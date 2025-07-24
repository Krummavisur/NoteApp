package com.example.note.hilt

import android.content.Context
import androidx.room.Room
import com.example.note.data.local.NotesAppDatabase
import com.example.note.data.local.NotesDao
import com.example.note.data.repository.LocalNotesRepository
import com.example.note.data.repository.NotesRepository
import com.example.note.data.encryption.CryptoManager
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
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesAppDatabase {
        return Room.databaseBuilder(
            context,
            NotesAppDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    fun provideNoteDao(db: NotesAppDatabase): NotesDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNotesRepository(
        dao: NotesDao,
        cryptoManager: CryptoManager
    ): NotesRepository {
        return LocalNotesRepository(dao, cryptoManager)
    }
}