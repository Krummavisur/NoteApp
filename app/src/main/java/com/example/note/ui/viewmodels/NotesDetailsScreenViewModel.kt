package com.example.note.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.repository.NotesRepository
import com.example.note.ui.screens.NotesDetailsScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesDetailsScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesDetailsScreenUIState())
    val uiState: StateFlow<NotesDetailsScreenUIState> = _uiState.asStateFlow()

    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val note = repository.getNoteById(noteId)
                _uiState.update { it.copy(note = note, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Unknown error", isLoading = false) }
            }
        }
    }

    fun saveNote(noteId: Int?, title: String, content: String, isFinished: Boolean = false) {
        viewModelScope.launch {
            try {
                if (noteId == null) {
                    repository.addNote(title, content, isFinished)
                } else {
                    repository.updateNote(noteId, title, content, isFinished)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}