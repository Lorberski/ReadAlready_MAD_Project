package com.example.readalready_mad_project.states

import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.components.FilterOption

data class SearchState(
    val allBooks: List<BookEntity> = emptyList(),
    //val books: List<BookEntity> = emptyList(),
    val filter: FilterOptionSearchState = FilterOptionSearchState.Title
)
enum class FilterOptionSearchState(override val label: String): FilterOption {
    Title("Title"),
    Author("Author"),
    Isbn("Isbn")
}
