package com.example.note.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.note.ui.viewmodels.NotesMainScreenViewModel


@Composable
fun NotesMainScreen() {

}

@Composable
fun NotesCard(
    modifier: Modifier = Modifier,
    viewModel: NotesMainScreenViewModel
    ) {
    val notes by viewModel.notes.collectAsState()
    Card(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = notes)
    }
}