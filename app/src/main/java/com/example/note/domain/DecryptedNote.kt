package com.example.note.domain

data class DecryptedNote(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isFavorite: Boolean
)
