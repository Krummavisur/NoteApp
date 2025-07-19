package com.example.note.use_cases

import com.example.note.data.repository.NotesRepository
import javax.inject.Inject

class AddNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(title: String, content: String, isFavorite: Boolean) {
        repository.addOrUpdateNote(title, content, isFavorite)
    }
}