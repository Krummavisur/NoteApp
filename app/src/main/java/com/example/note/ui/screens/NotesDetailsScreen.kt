package com.example.note.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note.ui.viewmodels.NotesDetailsScreenViewModel

@Composable
fun NotesDetailsScreen(
    viewModel: NotesDetailsScreenViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf(uiState.note?.title ?: "") }
    var content by remember { mutableStateOf(uiState.note?.content ?: "") }

    LaunchedEffect(uiState.note) {
        title = uiState.note?.title ?: ""
        content = uiState.note?.content ?: ""
    }

    TitleTextField(
        title = title,
        onTitleChange = {title = it}
    )
    HorizontalDivider()

    ContentTextField(
        content = content,
        onContentChange = {content = it}
    )
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