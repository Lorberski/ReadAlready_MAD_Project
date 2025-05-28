package com.example.readalready_mad_project.states

import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.components.FilterOption


data class BooksState(
    val allBooks: List<BookEntity> = emptyList(),
    val books: List<BookEntity> = emptyList(),
    val filter: FilterOptionBookState = FilterOptionBookState.All
)


enum class FilterOptionBookState(override val label: String) : FilterOption {
    All("All"),
    AlreadyRead("Already Read"),
    NotRead("Not Read"),
    Author("Author"),
    Title("Title")
}

