package com.example.readalready_mad_project.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readalready_mad_project.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    fun searchBooks(query: String) {
        Log.d("BookRepository", "inViewModel")

//        repository.getBooks(query)
//            .onEach { books ->
//                Log.d("BookRepository", "Books received: ${books.size}")
//
//            }
//            .launchIn(viewModelScope)

        repository.getBooksFromDb()
            .onEach { books ->
                Log.d("BookRepository", "Books from DB: ${books.size}")
            }
            .launchIn(viewModelScope)
    }

}