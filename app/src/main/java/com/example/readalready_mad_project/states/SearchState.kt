package com.example.readalready_mad_project.states

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.readalready_mad_project.R
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.components.FilterOption

data class SearchState(
    val allBooks: List<BookEntity> = emptyList(),
    val filter: FilterOptionSearchState = FilterOptionSearchState.Title,
    var firstStart: Boolean = true,
    var firstSearch: Boolean = true
)
enum class FilterOptionSearchState(@StringRes val labelResId: Int): FilterOption {
    Title(R.string.title),
    Author(R.string.author),
    Isbn(R.string.isbn);

    @Composable
    override fun label(): String {
        val context = androidx.compose.ui.platform.LocalContext.current
        return context.getString(labelResId)
    }
}
