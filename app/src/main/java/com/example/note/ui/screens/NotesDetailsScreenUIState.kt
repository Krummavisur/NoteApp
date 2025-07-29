package com.example.note.ui.screens

import com.example.note.domain.Note

data class NotesDetailsScreenUIState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)