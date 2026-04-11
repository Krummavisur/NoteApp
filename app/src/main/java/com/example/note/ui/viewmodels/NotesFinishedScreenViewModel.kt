package com.example.note.ui.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.repository.NotesRepository
import com.example.note.ui.screens.NotesFinishedScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotesFinishedScreenViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesFinishedScreenUiState())
    val uiState: StateFlow<NotesFinishedScreenUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow(TextFieldValue(""))

    init {
        observeFinishedNotes()
    }

    private fun observeFinishedNotes() {
        viewModelScope.launch {
            combine(repository.getAllFinishedNotes(), _searchQuery) { notes, query ->
                val filtered = if (query.text.isBlank()) {
                    notes
                } else {
                    notes.filter {
                        it.title.contains(query.text, ignoreCase = true) ||
                                it.content.contains(query.text, ignoreCase = true)
                    }
                }
                filtered.sortedByDescending { it.timestamp }
            }.collect { filteredNotes ->
                _uiState.update { it.copy(
                    notes = filteredNotes,
                    isLoading = false
                ) }
            }
        }
    }

    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun toggleFinished(noteId: Int) {
        viewModelScope.launch {
            repository.toggleFinished(noteId)
        }
    }
}