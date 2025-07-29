package com.example.note.use_cases

import com.example.note.data.repository.NotesRepository
import com.example.note.domain.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(noteId: Int): Flow<Note?> {
        return flow {
            emit(repository.getNoteById(noteId))
        }
    }
}