package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.FilterOption
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.ui.components.BookImage
import com.example.readalready_mad_project.ui.components.SearchBar
import com.example.readalready_mad_project.viewmodel.BooksViewModel
import com.example.readalready_mad_project.viewmodel.SearchViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SearchScreenContent(){
    val viewModel: SearchViewmodel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearchTriggered = { viewModel.searchForBooks(searchQuery) })
        SearchMainContent(
            books = state.allBooks
        ) }
}


@Composable
fun SearchMainContent(
    books: List<BookEntity>,
) {
    val listState = rememberLazyListState()

    Row(modifier = Modifier.fillMaxSize()) {

        if (books.isNotEmpty()) {

            LazyColumn(state = listState) {
                items(books) { book ->
                    BookCard(book = book)  // Hier wird BookCard verwendet!
                }
            }
        }
    }
}

