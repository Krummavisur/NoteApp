package com.example.note.mapper

import com.example.note.data.local.NoteEntity
import com.example.note.domain.DecryptedNote
import com.example.note.encryption.CryptoManager

fun NoteEntity.toDecryptedNote(cryptoManager: CryptoManager): DecryptedNote? {
    return try {
        val decryptedContent = cryptoManager.decryptText(encryptedContent)
        DecryptedNote(
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