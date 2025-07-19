package com.example.note.ui.screens

import com.example.note.domain.DecryptedNotes

data class NotesMainScreenUiState(
    val notes: List<DecryptedNotes> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)