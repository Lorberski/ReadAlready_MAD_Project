package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.FilterOptionSearchState
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.ui.components.FilterBar
import com.example.readalready_mad_project.ui.components.SearchBar
import com.example.readalready_mad_project.viewmodel.SearchViewmodel


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
        FilterBar(
            selected = state.filter,
            onFilterSelected = viewModel::onSearchFilterChanged,
            options = FilterOptionSearchState.entries.toTypedArray()
        )
        SearchMainContent(
            books = state.allBooks
        )
    }
}


@Composable
fun SearchMainContent(
    books: List<BookEntity>,
    viewmodel: SearchViewmodel = hiltViewModel()
) {
    val listState = rememberLazyListState()


    Row(modifier = Modifier.fillMaxSize()) {

        if (books.isNotEmpty()) {

            LazyColumn(state = listState) {
                items(books) { book ->
                    BookCard(
                        book = book,
                        repositoryAddFunction = {viewmodel.addBook(book)}
                    ){
                        withTitle()
                        withAuthors()
                        withImage()
                        withDescription()
                        expandable()
                        withoutStatus()
                        withAddButton()
                    }
                }
            }
        }
    }
}

