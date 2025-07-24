package com.example.note.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.repository.NotesRepository
import com.example.note.ui.screens.NotesDetailsScreenUIState
import com.example.note.use_cases.AddNotesUseCase
import com.example.note.use_cases.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesDetailsScreenViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val addNotesUseCase: AddNotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesDetailsScreenUIState())
    val uiState: StateFlow<NotesDetailsScreenUIState> = _uiState.asStateFlow()

    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(noteId)
                .onStart {
                    _uiState.value = NotesDetailsScreenUIState(isLoading = true)
                }
                .catch { e ->
                    _uiState.value = NotesDetailsScreenUIState(error = e.message ?: "Unknown error")
                }
                .collect { note ->
                    _uiState.value = NotesDetailsScreenUIState(note = note)
                }
        }
    }

    fun saveNote(noteId: Int?, title: String, content: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                addNotesUseCase(title, content, isFavorite, noteId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}