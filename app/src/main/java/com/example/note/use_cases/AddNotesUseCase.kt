package com.example.note.use_cases

import com.example.note.data.repository.NotesRepository
import javax.inject.Inject

class AddNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
        isFavorite: Boolean,
        noteId: Int? = null
    ) {
        if (noteId == null) {
            repository.addNote(title,content,isFavorite)
        } else {
        repository.updateNote(noteId, title, content, isFavorite)
        }
    }
}