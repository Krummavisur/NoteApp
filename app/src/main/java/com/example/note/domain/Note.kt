package com.example.note.domain

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isFavorite: Boolean
)
