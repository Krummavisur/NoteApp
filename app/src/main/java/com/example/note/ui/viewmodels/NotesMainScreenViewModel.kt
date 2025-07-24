package com.example.note.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.repository.NotesRepository
import com.example.note.ui.screens.NotesMainScreenUiState
import com.example.note.use_cases.AddNotesUseCase
import com.example.note.use_cases.DeleteNotesUseCase
import com.example.note.use_cases.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesMainScreenViewModel @Inject constructor(
    private val searchNotesUseCase: SearchNotesUseCase,
    private val addNotesUseCase: AddNotesUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesMainScreenUiState())
    val uiState: StateFlow<NotesMainScreenUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchNotes("")
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchNotes(query)
    }

    private fun searchNotes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchNotesUseCase(query).collect { state ->
                _uiState.value = state
            }
        }
    }

    fun addNote(title: String, content: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                addNotesUseCase(title, content, isFavorite)
                searchNotes(_uiState.value.searchQuery)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                deleteNotesUseCase(noteId)
                searchNotes(_uiState.value.searchQuery)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun toggleFavorite(noteId: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(noteId)
        }
    }
}