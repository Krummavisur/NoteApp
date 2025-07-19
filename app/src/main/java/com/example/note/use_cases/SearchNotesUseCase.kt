package com.example.note.use_cases

import com.example.note.data.repository.NotesRepository
import com.example.note.domain.DecryptedNotes
import com.example.note.ui.screens.NotesMainScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(query: String): Flow<NotesMainScreenUiState> {
        return repository.getAllNotes()
            .map { notes ->
                val filteredNotes = if (query.isBlank()) {
                    notes
                } else {
                    notes.filter { it.title.contains(query, ignoreCase = true) }
                }

                NotesMainScreenUiState(
                    notes = filteredNotes,
                    searchQuery = query,
                    isLoading = false,
                    error = null
                )
            }
            .onStart {
                emit(NotesMainScreenUiState(isLoading = true))
            }
            .catch { e ->
                emit(
                    NotesMainScreenUiState(
                        notes = emptyList(),
                        searchQuery = query,
                        isLoading = false,
                        error = e.message
                    )
                )
            }
    }
}