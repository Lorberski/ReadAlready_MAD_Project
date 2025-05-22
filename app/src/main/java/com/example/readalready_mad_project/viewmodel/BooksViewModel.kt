package com.example.readalready_mad_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readalready_mad_project.data.database.BookDao
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.repository.BookRepository
import com.example.readalready_mad_project.states.BooksState
import com.example.readalready_mad_project.states.FilterOption
import dagger.hilt.android.lifecycle.HiltViewModel
import  javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BooksState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getBooks("Harry Potter").collect { books ->
                _state.update { it.copy(allBooks = books, books = applyFilter(books, it.filter))
                }
            }
        }
    }

    fun onFilterChanged(newFilter: FilterOption) {
        _state.update {
            val filtered = applyFilter(it.allBooks, newFilter)
            it.copy(filter = newFilter, books = filtered)
        }
    }

    fun fetchBooksFromApi(query: String = "android") {
        viewModelScope.launch {
            repository.getBooks(query).collect { books ->
                _state.update { it.copy(books = applyFilter(books, state.value.filter)) }
            }
        }
    }


    private fun applyFilter(books: List<BookEntity>, filter: FilterOption): List<BookEntity> {
        return when (filter) {
            FilterOption.AlreadyRead -> books.filter { it.alreadyRead }
            FilterOption.NotRead -> books.filter { !it.alreadyRead }
            FilterOption.Author -> books.sortedBy { it.authors?.firstOrNull() ?: "" }
            FilterOption.Title -> books.sortedBy { it.title }
            FilterOption.All -> books
        }
    }
}
