package com.example.note.ui.screens

import androidx.compose.ui.text.input.TextFieldValue
import com.example.note.domain.DecryptedNotes

data class NotesMainScreenUiState(
    val notes: List<DecryptedNotes> = emptyList(),
    val searchQuery: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String? = null
)