package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.FilterOptionBookState
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.viewmodel.BooksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavHostController
import com.example.readalready_mad_project.ui.Navigation
import com.example.readalready_mad_project.ui.components.FilterBar

@Composable
fun BooksScreenContent(navController: NavHostController) {
    val viewModel: BooksViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    MainContent(
        books = state.books,
        selectedFilter = state.filter,
        onFilterChange = viewModel::onFilterChanged,
        onBookClick = { bookId ->
            navController.navigate(Navigation.BookDetailScreen.createRoute(bookId))
        }
    )
}



@Composable
fun MainContent(
    books: List<BookEntity>,
    selectedFilter: FilterOptionBookState,
    onFilterChange: (FilterOptionBookState) -> Unit,
    onBookClick: (String) -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val sortedBooks = remember(books) { books.sortedBy { it.title } }
    val listState = rememberLazyListState()
    val scrollToIndex = remember { mutableStateOf<Int?>(null) }

    // Smooth scroll in proper coroutine scope
    LaunchedEffect(scrollToIndex.value) {
        scrollToIndex.value?.let { index ->
            if (index >= 0 && index < listState.layoutInfo.totalItemsCount) {
                listState.animateScrollToItem(index)
            }
            scrollToIndex.value = null
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            FilterBar(
                selected = selectedFilter,
                onFilterSelected = onFilterChange,
                options = FilterOptionBookState.entries.toTypedArray(),
                filterTitleText = "Filter"
            )

            LazyColumn(state = listState) {
                items(sortedBooks) { book ->
                    BookCard(
                        book = book,
                        repositoryAddFunction = { viewModel.deleteBook(book.id) },
                        onClick = { onBookClick(book.id) }
                    ) {
                        withStatus()
                    }
                }
            }
        }

        if (books.isNotEmpty()) {
            AlphabetScrollbar(
                books = sortedBooks,
                onLetterClicked = { letter ->
                    val index = sortedBooks.indexOfFirst {
                        it.title.isNotBlank() &&
                                it.title.firstOrNull()?.equals(letter, ignoreCase = true) == true
                    }
                    scrollToIndex.value = index
                }
            )
        }
    }
}


@Composable
fun AlphabetScrollbar(
    books: List<BookEntity>,
    onLetterClicked: (Char) -> Unit
) {
    // Set of first letters available
    val availableLetters = remember(books) {
        books.mapNotNull { it.title.firstOrNull()?.uppercaseChar() }.toSet()
    }

    Column(
        modifier = Modifier
            .width(28.dp)
            .fillMaxHeight()
            .padding(end = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ('A'..'Z').forEach { letter ->
            val isAvailable = letter in availableLetters

            Text(
                text = letter.toString(),
                fontSize = 12.sp,
                color = if (isAvailable) androidx.compose.ui.graphics.Color.LightGray
                else androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier
                    .padding(2.dp)
                    .clickable(enabled = isAvailable) {
                        onLetterClicked(letter)
                    }
            )
        }
    }
}
