package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.BookDetailState
import com.example.readalready_mad_project.states.FilterOptionBookState
import com.example.readalready_mad_project.ui.Navigation
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.viewmodel.BookDetailViewModel
import com.example.readalready_mad_project.viewmodel.BooksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreenContent(bookId: String, navController: NavHostController) {
    val viewModel: BookDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

    MainContent(
        navController = navController,
        state = state,
        onBackButton = {
            navController.navigate(Navigation.BooksScreen.route)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel: BookDetailViewModel = hiltViewModel(),
    navController: NavHostController,
    state: BookDetailState,
    onBackButton:() -> Unit,


    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onBackButton) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Zurück"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.book != null -> {
                    val book = state.book!!

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        BookCard(
                            book = book,
                            repositoryDeleteFunction = {
                                viewModel.deleteBook()
                                navController.popBackStack() },
                            repositoryToggleFunction = { viewModel.toggleReadStatus() },
                            onClick = null,
                            configBuilder = {
                                notExpandable()
                                withStatus()
                                withDeleteSymbol()
                                withAlreadyReadButton()
                            }
                        )
                    }
                }
            }
        }
    }
}
