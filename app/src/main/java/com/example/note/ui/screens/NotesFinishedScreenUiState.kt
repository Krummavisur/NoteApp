package com.example.note.ui.screens

import androidx.compose.ui.text.input.TextFieldValue
import com.example.note.domain.Note

data class NotesFinishedScreenUiState(
    val notes: List<Note> = emptyList(),
    val searchQuery: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String? = null
)