package com.example.readalready_mad_project.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.repository.BookRepository
import com.example.readalready_mad_project.states.BooksState
import com.example.readalready_mad_project.states.FilterOption
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

    fun searchForBooks(title: String? = null, author: String? = null, isbn: String? = null){
        viewModelScope.launch {
            repository.getBooksFromApi(
                title = title,
                author = author,
                isbn = isbn

            ).collect { books ->
                _state.update { it.copy(allBooks = books)
                }
            }
        }
        }

    fun addBook(book: BookEntity){
        viewModelScope.launch {
            repository.addBookToDb(book)
        }
    }
}
