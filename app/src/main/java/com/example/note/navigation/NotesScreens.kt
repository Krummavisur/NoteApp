package com.example.note.navigation

import android.graphics.drawable.Icon
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.note.ui.screens.NotesDetailsScreen
import com.example.note.ui.screens.NotesMainScreen
import com.example.note.ui.viewmodels.NotesDetailsScreenViewModel
import com.example.note.ui.viewmodels.NotesMainScreenViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


enum class NotesScreens (val route: String) {
    MainScreen(route = "main"),
    DetailsScreen(route = "details/{noteId}");

    companion object {
        fun fromRoute(route: String?): NotesScreens =
            NotesScreens.values().firstOrNull { route?.startsWith(it.route) == true } ?: MainScreen
    }
}

@ExperimentalMaterial3Api
@Composable
fun NotesTopAppBar(
    title: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    showSearchIcon: Boolean,
    onSearchIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        },
        actions = {
            if (showSearchIcon) {
                IconButton(onClick = onSearchIconClick) {
                    Icon(
                        contentDescription = "Поиск",
                        imageVector = Icons.Filled.Search,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NotesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NotesScreens.fromRoute(currentBackStackEntry?.destination?.route)
    val viewModel: NotesMainScreenViewModel = hiltViewModel()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    Scaffold(
        topBar = {
            when (currentScreen) {
                NotesScreens.MainScreen -> {
                    NotesTopAppBar(
                        title = "Заметки",
                        showBackButton = false,
                        onBackClick = {},
                        showSearchIcon = true,
                        onSearchIconClick = {viewModel.toggleSearch()}
                    )
                }

                NotesScreens.DetailsScreen -> {
                    NotesTopAppBar(
                        title = "Заметки",
                        showBackButton = true,
                        onBackClick = { navController.popBackStack() },
                        showSearchIcon = false,
                        onSearchIconClick = {}
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface (
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
        AnimatedNavHost(
            navController = navController,
            startDestination = NotesScreens.MainScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth / 4 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth / 4 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
        ) {
            composable(NotesScreens.MainScreen.route) {
                val notesMainScreenViewModel: NotesMainScreenViewModel = hiltViewModel()
                NotesMainScreen(
                    onNoteClick = { noteId ->
                        navController.navigate(route = "details/${noteId}") },
                    viewModel = notesMainScreenViewModel,
                    isSearchActive = isSearchActive,
                    contentPadding = innerPadding
                )
            }
            composable(NotesScreens.DetailsScreen.route) {
                val notesDetailsScreenViewModel: NotesDetailsScreenViewModel = hiltViewModel()
                NotesDetailsScreen(
                    viewModel = notesDetailsScreenViewModel,
                    contentPadding = innerPadding
                    )
                }
            }
        }
    }
}