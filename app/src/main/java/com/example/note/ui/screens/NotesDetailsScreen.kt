package com.example.note.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note.ui.viewmodels.NotesDetailsScreenViewModel

@Composable
fun NotesDetailsScreen(
    noteId: Int,
    viewModel: NotesDetailsScreenViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    var title by remember { mutableStateOf(uiState.note?.title ?: "") }
    var content by remember { mutableStateOf(uiState.note?.content ?: "") }

    LaunchedEffect(uiState.note) {
        title = uiState.note?.title ?: ""
        content = uiState.note?.content ?: ""
    }

    Column(
        modifier = Modifier
            .padding(contentPadding) // <- вот сюда падают отступы от Scaffold с topBar
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleTextField(
            title = title,
            onTitleChange = { title = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContentTextField(
            content = content,
            onContentChange = { content = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveNote(noteId, title, content)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Сохранить")
        }
    }
}

@Composable
fun TitleTextField(
    title: String,
    onTitleChange: (String) -> Unit
) {
    TextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text("Заголовок") }
    )
}

@Composable
fun ContentTextField(
    content: String,
    onContentChange: (String) -> Unit
) {
    TextField(
        value = content,
        onValueChange = onContentChange,
        label = { Text("Содержимое") }
    )
}