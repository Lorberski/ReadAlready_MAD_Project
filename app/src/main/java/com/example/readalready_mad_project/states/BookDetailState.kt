package com.example.readalready_mad_project.states

import com.example.readalready_mad_project.data.database.BookEntity

data class BookDetailState(
    val book: BookEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
