package com.example.note.ui.viewmodels


import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.repository.NotesRepository
import com.example.note.ui.screens.NotesMainScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesMainScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesMainScreenUiState())
    val uiState: StateFlow<NotesMainScreenUiState> = _uiState.asStateFlow()
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()
    private val _searchQuery = MutableStateFlow(TextFieldValue(""))

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            combine(repository.getAllNotes(), _searchQuery) { notes, query ->
                val filtered = if (query.text.isBlank()) {
                    notes
                } else {
                    notes.filter {
                        it.title.contains(query.text, ignoreCase = true) ||
                                it.content.contains(query.text, ignoreCase = true)
                    }
                }
                filtered.sortedByDescending { it.isFinished }
            }.onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.catch { e ->
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }.collect { filteredNotes ->
                _uiState.update { it.copy(
                    notes = filteredNotes,
                    isLoading = false,
                    searchQuery = _searchQuery.value
                ) }
            }
        }
    }

    fun loadNotes() {
        _searchQuery.value = TextFieldValue("")
    }

    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
    }

    fun disableSearch() {
        _isSearchActive.value = false
        _searchQuery.value = TextFieldValue("")
    }

    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String, isFinished: Boolean = false) {
        viewModelScope.launch {
            try {
                repository.addNote(title, content, isFinished)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun toggleFinished(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.toggleFinished(noteId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}