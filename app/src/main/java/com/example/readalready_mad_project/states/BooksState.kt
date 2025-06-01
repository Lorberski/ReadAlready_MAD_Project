package com.example.readalready_mad_project.states

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.readalready_mad_project.R
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.components.FilterOption


data class BooksState(
    val allBooks: List<BookEntity> = emptyList(),
    val books: List<BookEntity> = emptyList(),
    val filter: FilterOptionBookState = FilterOptionBookState.All
)


enum class FilterOptionBookState(@StringRes val labelResId: Int) : FilterOption {
    All(R.string.filter_all),
    AlreadyRead(R.string.already_read),
    NotRead(R.string.unread),
    Author(R.string.author),
    Title(R.string.title);

    @Composable
    override fun label(): String {
        val context = androidx.compose.ui.platform.LocalContext.current
        return context.getString(labelResId)
    }
}

