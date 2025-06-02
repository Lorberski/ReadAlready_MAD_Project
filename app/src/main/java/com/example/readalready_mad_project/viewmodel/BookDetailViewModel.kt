package com.example.readalready_mad_project.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readalready_mad_project.data.repository.BookRepository
import com.example.readalready_mad_project.states.BookDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    private val _showDescription = MutableStateFlow(false)
    val showDescription: StateFlow<Boolean> = _showDescription

    private val _showNotesEditor = MutableStateFlow(false)
    val showNotesEditor: StateFlow<Boolean> = _showNotesEditor

    private val _notesInput = MutableStateFlow("")
    val notesInput: StateFlow<String> = _notesInput

    private val _isEditingNotes = MutableStateFlow(false)
    val isEditingNotes: StateFlow<Boolean> = _isEditingNotes

    fun startEditingNotes() {
        _isEditingNotes.value = true
    }

    fun stopEditingNotes() {
        _isEditingNotes.value = false
    }

    init {
        val bookId = savedStateHandle.get<String>("bookId")
        bookId?.let { loadBook(it) }
    }

    fun toggleNotesEditor() {
        _showNotesEditor.value = !_showNotesEditor.value
        if (_showNotesEditor.value) {
            _showDescription.value = false
        }
    }

    fun onNotesChanged(newValue: String) {
        _notesInput.value = newValue
    }

    fun saveNotes() {
        val currentBook = _state.value.book
        if (currentBook != null) {
            val updatedBook = currentBook.copy(notes = _notesInput.value)
            _state.update { it.copy(book = updatedBook) }
            viewModelScope.launch {
                repository.updateBookInDb(updatedBook)
            }
        }
    }

    fun deleteNotes() {
        val currentBook = _state.value.book
        if (currentBook != null) {
            val updatedBook = currentBook.copy(notes = null)
            _state.update { it.copy(book = updatedBook) }
            _notesInput.value = ""
            viewModelScope.launch {
                repository.updateBookInDb(updatedBook)
            }
        }
    }



    fun loadBook(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val book = repository.getBookById(bookId)
                _state.update { it.copy(book = book, isLoading = false) }
                if (book != null) {
                    _notesInput.value = book.notes ?: ""
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun toggleDescriptionVisibility() {
        _showDescription.value = !_showDescription.value
        if (_showDescription.value) {
            _showNotesEditor.value = false
        }
    }


    fun toggleReadStatus() {
        val currentBook = _state.value.book
        if (currentBook != null) {
            val updateBook = currentBook.copy(alreadyRead = !currentBook.alreadyRead)
            _state.update { it.copy(book = updateBook) }
            viewModelScope.launch {
                repository.updateBookInDb(updateBook)
            }
        }
    }

    fun deleteBook() {
        val currentBook = _state.value.book
        if (currentBook != null) {
            viewModelScope.launch {
                repository.deleteBookFromDB(currentBook.id)
            }
        }
    }
}