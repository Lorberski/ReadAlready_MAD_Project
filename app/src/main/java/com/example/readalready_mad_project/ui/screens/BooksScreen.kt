package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
            navController.navigate("book_detail/$bookId")
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
    val listState = rememberLazyListState()

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
        FilterBar(
            selected = selectedFilter,
            onFilterSelected = onFilterChange,
            options = FilterOptionBookState.entries.toTypedArray()
        )
            LazyColumn(state = listState) {
                items(books) { book ->
                    BookCard(
                        book = book,
                        repositoryFunction = { viewModel.deleteBook(book.id) },
                        onClick = { onBookClick(book.id) }
                    ){
                        withDeleteSymbol()
                        withStatus()
                    }
                }
            }
        }

        if (books.isNotEmpty()) {
            AlphabetScrollbar(
                books = books,
                onLetterClicked = { letter ->
                    val index = books.indexOfFirst {
                        it.title.startsWith(letter, ignoreCase = true)
                    }
                    if (index >= 0) {
                        CoroutineScope(Dispatchers.Main).launch {
                            listState.animateScrollToItem(index)
                        }
                    }
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
    Column(
        modifier = Modifier
            .width(28.dp)
            .fillMaxHeight()
            .padding(end = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ('A'..'Z').forEach { letter ->
            Text(
                text = letter.toString(),
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable { onLetterClicked(letter) }
                    .padding(2.dp)
            )
        }
    }
}
