package com.example.note.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
    modifier: Modifier = Modifier,
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
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
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
}

@Composable
fun TitleTextField(
    title: String,
    onTitleChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text("Заголовок") },
        textStyle = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.outline
        )

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
        label = { Text("Содержимое") },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        maxLines = Int.MAX_VALUE,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.outline
        )
    )
}