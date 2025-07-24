package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.note.navigation.NotesApp
import com.example.note.ui.screens.NotesMainScreen
import com.example.note.ui.theme.NoteTheme
import com.example.note.ui.viewmodels.NotesMainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteTheme {
                NotesApp()
            }
        }
    }
}
