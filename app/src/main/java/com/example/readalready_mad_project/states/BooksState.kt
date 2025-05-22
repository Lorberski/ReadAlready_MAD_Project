package com.example.readalready_mad_project.states

import com.example.readalready_mad_project.data.database.BookEntity


data class BooksState(
    val allBooks: List<BookEntity> = emptyList(),
    val books: List<BookEntity> = emptyList(),
    val filter: FilterOption = FilterOption.All
)

enum class FilterOption(val label: String) {
    All("All"),
    AlreadyRead("Already Read"),
    NotRead("Not Read"),
    Author("Author"),
    Title("Title")
}

