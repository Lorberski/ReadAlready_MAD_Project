package com.example.readalready_mad_project.states

import com.example.readalready_mad_project.data.database.BookEntity

data class SearchState(
    val allBooks: List<BookEntity> = emptyList(),
    //TODO  Add filter
    //val books: List<BookEntity> = emptyList(),
    //val filter: FilterOption = FilterOption.All
)
