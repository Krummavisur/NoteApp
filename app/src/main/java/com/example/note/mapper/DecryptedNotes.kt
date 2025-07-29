package com.example.note.mapper

import com.example.note.data.local.NotesEntity
import com.example.note.domain.Note
import com.example.note.data.encryption.CryptoManager

fun NotesEntity.toDecryptedNote(cryptoManager: CryptoManager): Note? {
    return try {
        val decryptedContent = cryptoManager.decryptText(encryptedContent)
        Note(
            id = id,
            title = title,
            content = decryptedContent,
            timestamp = timestamp,
            isFavorite = isFavorite
        )
    } catch (e: Exception) {
        null
    }
}