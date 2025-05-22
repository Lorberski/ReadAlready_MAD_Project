package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.states.FilterOption
import com.example.readalready_mad_project.viewmodel.BooksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*



@Composable
fun BooksScreenContent(){
    MainContent()
}

@Composable
fun MainContent(viewModel: BooksViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            FilterBar(
                selected = state.filter,
                onFilterSelected = { viewModel.onFilterChanged(it) }
            )
            LazyColumn(state = listState) {
                items(state.books) { book ->
                    BookItem(book)
                }
            }
        }

        // Alphabet Scrollbar
        Column(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
                .padding(end = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ('A'..'Z').forEach { letter ->
                Text(
                    text = letter.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable {
                            val index = state.books.indexOfFirst { it.title.startsWith(letter, ignoreCase = true) }
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
}

@Composable
fun FilterBar(
    selected: FilterOption,
    onFilterSelected: (FilterOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Filter: ${selected.label}")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            FilterOption.values().forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        onFilterSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun BookItem(book: BookEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = book.title, fontWeight = FontWeight.Bold)
        Text(text = "Author: ${book.authors}")
        Text(text = if (book.alreadyRead) "Already read" else "Not read")
    }
}
