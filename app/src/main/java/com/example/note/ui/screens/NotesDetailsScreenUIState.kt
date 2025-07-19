package com.example.note.ui.screens

import com.example.note.domain.DecryptedNotes

data class NotesDetailsScreenUIState(
    val note: DecryptedNotes? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)