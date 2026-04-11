package com.example.note.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note.ui.viewmodels.NotesFinishedScreenViewModel

@Composable
fun NotesFinishedScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesFinishedScreenViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit,
    contentPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    var isSearchActive by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isSearchActive) {
                    Text(
                        "Архив выполненных",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    IconButton(onClick = { isSearchActive = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                } else {
                    Box(modifier = Modifier.weight(1f)) {
                        SearchNote(
                            uiState = uiState,
                            onQueryChange = viewModel::onSearchQueryChanged
                        )
                    }
                    CancelButton(onCancelClick = {
                        isSearchActive = false
                        viewModel.onSearchQueryChanged(TextFieldValue(""))
                    })
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.notes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { onNoteClick(note.id) },
                        onDeleteClick = { viewModel.deleteNote(note.id) },
                        onFinishedClick = { viewModel.toggleFinished(note.id) }
                    )
                }
            }
        }
    }
}
@Composable
fun SearchNote(
    uiState: NotesFinishedScreenUiState,
    onQueryChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = uiState.searchQuery,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Поиск...") },
        singleLine = true
    )
}