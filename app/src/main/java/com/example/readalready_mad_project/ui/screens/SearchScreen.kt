package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.readalready_mad_project.R
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.FilterOptionSearchState
import com.example.readalready_mad_project.ui.components.BookCard
import com.example.readalready_mad_project.ui.components.FilterBar
import com.example.readalready_mad_project.ui.components.SearchBar
import com.example.readalready_mad_project.viewmodel.SearchViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SearchScreenContent(navController: NavController){
    val viewModel: SearchViewmodel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentEntry = rememberUpdatedState(currentBackStackEntry)

    DisposableEffect(currentEntry.value) {
        onDispose {
            if (!navController.currentDestination?.route.orEmpty().contains("search")) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    viewModel.setFirstStart(true)
                    viewModel.setFirstSearch(true)
                    viewModel.showTrendingBooks()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (state.firstStart) {
            viewModel.showTrendingBooks()
            viewModel.setFirstStart(false)
        }
    }

    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearchTriggered = {
                viewModel.showBookSearchResult(searchQuery)
                viewModel.setFirstSearch(false)
            })
        FilterBar(
            selected = state.filter,
            onFilterSelected = viewModel::onSearchFilterChanged,
            options = FilterOptionSearchState.entries.toTypedArray(),
            filterTitleText =
                (stringResource(id = R.string.search_by))        )

        if (state.firstSearch){
            Text(
                text = (stringResource(id = R.string.trending_books)),
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
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

