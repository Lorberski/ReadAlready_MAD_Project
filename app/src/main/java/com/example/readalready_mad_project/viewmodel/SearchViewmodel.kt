package com.example.readalready_mad_project.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.repository.BookRepository
import com.example.readalready_mad_project.states.FilterOptionSearchState
import com.example.readalready_mad_project.states.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import  javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewmodel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun searchForBooks(query: String) {
        viewModelScope.launch {
            val currentFilter = _state.value.filter

            val title = if (currentFilter == FilterOptionSearchState.Title) query else null
        val author = if (currentFilter == FilterOptionSearchState.Author) query else null
            val isbn = if (currentFilter == FilterOptionSearchState.Isbn) query else null

            repository.getBooksFromApi(
                title = title,
                author = author,
                isbn = isbn
            ).collect { books ->
                _state.update { it.copy(allBooks = books) }
            }
        }
    }


    fun addBook(book: BookEntity){
        viewModelScope.launch {
            repository.addBookToDb(book)
        }
    }

    fun onSearchFilterChanged(newFilter: FilterOptionSearchState) {
        _state.update { currentState ->
            currentState.copy(filter = newFilter)
        }
    }

}
