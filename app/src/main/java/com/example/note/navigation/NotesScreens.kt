package com.example.note.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
    onBackClick: () -> Unit
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NotesApp(
    navController: NavHostController = rememberAnimatedNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NotesScreens.fromRoute(currentBackStackEntry?.destination?.route)
    Scaffold(
        topBar = {
            when (currentScreen) {
                NotesScreens.MainScreen -> {
                    NotesTopAppBar(
                        title = "Заметки",
                        showBackButton = false,
                        onBackClick = {}
                    )
                }

                NotesScreens.DetailsScreen -> {
                    NotesTopAppBar(
                        title = "Заметки",
                        showBackButton = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    ) { padding ->
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
                    contentPadding = padding
                )
            }
            composable(NotesScreens.DetailsScreen.route) {
                val notesDetailsScreenViewModel: NotesDetailsScreenViewModel = hiltViewModel()
                NotesDetailsScreen(
                    viewModel = notesDetailsScreenViewModel,
                    contentPadding = padding
                )
            }
        }
    }
}