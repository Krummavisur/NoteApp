package com.example.note.use_cases

import com.example.note.data.repository.NotesRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.deleteNote(noteId)
    }
}