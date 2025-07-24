package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
            NoteTheme { Surface(modifier = Modifier.fillMaxSize()) {
                NotesApp()
                }
            }
        }
    }
}
