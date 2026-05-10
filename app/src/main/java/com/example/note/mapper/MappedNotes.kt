package com.example.note.mapper

import android.util.Log
import com.example.note.data.local.NotesEntity
import com.example.note.domain.Note

fun NotesEntity.toMappedNote(): Note? {
    return try {
        Note(
            id = id,
            title = title,
            content = content,
            timestamp = timestamp,
            isFinished = isFinished
        )
    } catch (e: Exception) {
        Log.e("NotesMapper", "Error mapping", e)
        null
    }
}