package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.readalready_mad_project.states.BookDetailState
import com.example.readalready_mad_project.ui.Navigation
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.viewmodel.BookDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreenContent(bookId: String, navController: NavHostController) {
    val viewModel: BookDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val showDescription by viewModel.showDescription.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

    MainContent(
        navController = navController,
        state = state,
        showDescription = showDescription,
        onToggleDescription = { viewModel.toggleDescriptionVisibility() },
        onBackButton = {
            navController.navigate(Navigation.BooksScreen.route)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavHostController,
    state: BookDetailState,
    showDescription: Boolean,
    onToggleDescription: () -> Unit,
    onBackButton: () -> Unit
) {
    val viewModel: BookDetailViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onBackButton) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = "Fehler: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.book != null -> {
                    val book = state.book!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        BookCard(
                            book = book,
                            repositoryDeleteFunction = {
                                viewModel.deleteBook()
                                navController.popBackStack()
                            },
                            repositoryReadToggleFunction = { viewModel.toggleReadStatus() },
                            onClick = null,
                            configBuilder = {
                                notExpandable()
                                withStatus()
                                withDeleteSymbol()
                                withAlreadyReadButton()
                                withNotesButton()
                                withDescriptionButton(onClick = onToggleDescription)                            }
                        )

                        if (showDescription) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Text(
                                    text = book.description ?: "Keine Beschreibung vorhanden.",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

