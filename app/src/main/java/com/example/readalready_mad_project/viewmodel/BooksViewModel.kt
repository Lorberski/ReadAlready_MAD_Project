package com.example.readalready_mad_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.repository.BookRepository
import com.example.readalready_mad_project.states.BooksState
import com.example.readalready_mad_project.states.FilterOptionBookState
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
            repository.getBooksFromDb().collect { books ->
                _state.update { it.copy(allBooks = books, books = applyFilter(books, it.filter))
                }
            }
        }
    }

    fun onFilterChanged(newFilter: FilterOptionBookState) {
        _state.update {
            val filtered = applyFilter(it.allBooks, newFilter)
            it.copy(filter = newFilter, books = filtered)
        }
    }


    fun deleteBook(bookId: String){
        viewModelScope.launch {
        repository.deleteBookFromDB(bookId)
        }
    }


    private fun applyFilter(books: List<BookEntity>, filter: FilterOptionBookState): List<BookEntity> {
        return when (filter) {
            FilterOptionBookState.AlreadyRead -> books.filter { it.alreadyRead }
            FilterOptionBookState.NotRead -> books.filter { !it.alreadyRead }
            FilterOptionBookState.Author -> books.sortedBy { it.authors?.firstOrNull() ?: "" }
            FilterOptionBookState.Title -> books.sortedBy { it.title }
            FilterOptionBookState.All -> books
        }
    }
}
