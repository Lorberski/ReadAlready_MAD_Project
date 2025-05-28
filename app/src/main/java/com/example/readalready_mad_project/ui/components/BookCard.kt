package com.example.readalready_mad_project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Description


import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.theme.ReadAlreadyTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


data class BookCardConfig(
    val showTitle: Boolean = true,
    val showAuthors: Boolean = true,
    val showPublisher: Boolean = false,
    val showPublishedDate: Boolean = false,
    val showPageCount: Boolean = false,
    val showRating: Boolean = false,
    val showDescription: Boolean = false,
    val showImage: Boolean = true,
    val expandable: Boolean = true,
    val showStatus: Boolean = false,
    val showAddButton: Boolean = false,
    val showDeleteSymbol: Boolean = false,
    val showAlreadyReadButton: Boolean = false,
    val showNotesButton: Boolean = false,
    val showDescriptionButton: Boolean = false,
    val descriptionToggleClick: (() -> Unit)? = null,
)

class BookCardConfigBuilder {
    private var showTitle = true
    private var showAuthors = true
    private var showPublisher = false
    private var showPublishedDate = false
    private var showPageCount = false
    private var showRating = false
    private var showDescription = false
    private var showImage = true
    private var expandable = true
    private var showStatus = false
    private var showAddButton = false
    private var showDeleteSymbol = false
    private var showAlreadyReadButton = false
    private var showNotesButton = false
    private var showDescriptionButton = false
    private var descriptionToggleClick: (() -> Unit)? = null


    fun withTitle() = apply { showTitle = true }
    fun withoutTitle() = apply { showTitle = false }

    fun withAuthors() = apply { showAuthors = true }
    fun withoutAuthors() = apply { showAuthors = false }

    fun withPublisher() = apply { showPublisher = true }
    fun withoutPublisher() = apply { showPublisher = false }

    fun withPublishedDate() = apply { showPublishedDate = true }
    fun withoutPublishedDate() = apply { showPublishedDate = false }

    fun withPageCount() = apply { showPageCount = true }
    fun withoutPageCount() = apply { showPageCount = false }

    fun withRating() = apply { showRating = true }
    fun withoutRating() = apply { showRating = false }

    fun withDescription() = apply { showDescription = true }
    fun withoutDescription() = apply { showDescription = false }

    fun withImage() = apply { showImage = true }
    fun withoutImage() = apply { showImage = false }

    fun expandable() = apply { expandable = true }
    fun notExpandable() = apply { expandable = false }

    fun withStatus() = apply { showStatus = true }
    fun withoutStatus() = apply { showStatus = false }

    fun withAddButton() = apply { showAddButton = true }
    fun withoutAddButton() = apply { showAddButton = false }

    fun withDeleteSymbol() = apply { showDeleteSymbol = true }
    fun withoutDeleteSymbol() = apply { showDeleteSymbol = false }

    fun withAlreadyReadButton() = apply { showAlreadyReadButton = true }
    fun withoutAlreadyReadButton() = apply { showAlreadyReadButton = false }

    fun withNotesButton() = apply { showNotesButton = true }
    fun withoutNotesButton() = apply { showNotesButton = false }

    fun withDescriptionButton(onClick: () -> Unit) = apply {
        showDescriptionButton = true
        descriptionToggleClick = onClick
    }
    fun withoutDescriptionButton() = apply { showDescriptionButton = false }

    fun build(): BookCardConfig {
        return BookCardConfig(
            showTitle,
            showAuthors,
            showPublisher,
            showPublishedDate,
            showPageCount,
            showRating,
            showDescription,
            showImage,
            expandable,
            showStatus,
            showAddButton,
            showDeleteSymbol,
            showAlreadyReadButton,
            showNotesButton,
            showDescriptionButton,
                    descriptionToggleClick
        )
    }
}


@Composable
fun BookCard(
    book: BookEntity,
    modifier: Modifier = Modifier,
    repositoryAddFunction: (() -> Unit)? = null,
    repositoryDeleteFunction: (() -> Unit)? = null,
    repositoryReadToggleFunction: (() -> Unit)? = null,
    repositoryNotesToggleFunction: (() -> Unit)? = null,
    repositoryDescriptionToggleFunction: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    configBuilder: (BookCardConfigBuilder.() -> Unit)? = null


) {
    val config = remember(configBuilder) {
        BookCardConfigBuilder().apply { configBuilder?.invoke(this) }.build()
    }

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .heightIn(min = 100.dp, max = if (config.expandable && expanded) 3000.dp else 200.dp)
            .clickable(enabled = config.expandable) {
                onClick?.invoke() ?: run {
                    expanded = !expanded
                }
            },

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.shape
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            Column {
                if (config.showImage) {
                    BookImage(book.thumbnail)
                }

                if (!config.expandable || expanded) {
                    if (config.showAddButton) {
                        if (repositoryAddFunction != null) {
                            Button(
                                onClick = repositoryAddFunction,
                                modifier = Modifier
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }


            Column(modifier = Modifier.align(Alignment.Top)) {
                if (config.showTitle) {
                    Text(book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                if (config.showAuthors) {
                    Text("Author: ${book.authors?.joinToString(", ")}", fontSize = 14.sp)
                }
                if (config.showStatus) {
                    Text(
                        text = if (book.alreadyRead) "Status: Already read" else "Status: Not read",
                        fontSize = 13.sp
                    )
                }
            }

            if (!config.expandable || expanded) {
                if (config.showPublisher) {
                    Text("Publisher: ${book.publisher}", fontSize = 13.sp)
                }
                if (config.showPublishedDate) {
                    Text("Published: ${book.publishedDate}", fontSize = 13.sp)
                }
                if (config.showPageCount) {
                    Text("Pages: ${book.pageCount}", fontSize = 13.sp)
                }
                if (config.showRating) {
                    Text(
                        "Rating: ${book.averageRating} (${book.ratingsCount} ratings)",
                        fontSize = 13.sp
                    )
                }
                if (config.showDescription) {
                    Text("Description: ${book.description}", fontSize = 13.sp)
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (config.showDeleteSymbol) {
                IconButton(onClick = {
                    if (repositoryDeleteFunction != null) {
                        repositoryDeleteFunction()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            if (config.showNotesButton) {
                IconButton(onClick = {
                    if (repositoryNotesToggleFunction != null) {
                        repositoryNotesToggleFunction()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.AddComment,
                        contentDescription = "Notes"
                    )
                }
            }

            if (config.showDescriptionButton) {
                IconButton(onClick = {
                    config.descriptionToggleClick?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Description,
                        contentDescription = "Beschreibung ein-/ausblenden"
                    )
                }
            }


            if (config.showAlreadyReadButton) {
                if (repositoryReadToggleFunction != null) {
                    IconButton(
                        onClick = repositoryReadToggleFunction,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (book.alreadyRead)
                                Icons.Default.MenuBook
                            else
                                Icons.Default.Book,
                            contentDescription = if (book.alreadyRead) "Already read" else "Status: Not read",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookImage(imageUrl: String?) {

    val secureUrl = imageUrl?.replace("http://", "https://")
    AsyncImage(
        model = secureUrl,
        contentDescription = "Buchcover",
        modifier = Modifier
            .height(100.dp)
            .padding(end = 12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun BookCardPreview() {
    ReadAlreadyTheme {
        BookCard(
            BookEntity(
                id = "",
                title = "Example Book",
                authors = emptyList(),
                publisher = "Publisher",
                publishedDate = "1.1.1999",
                description = "Some Text",
                pageCount = 5,
                categories = emptyList(),
                averageRating = 0.0,
                ratingsCount = 0,
                smallThumbnail = "TODO()",
                thumbnail = "TODO()",
                alreadyRead = false
            ),
            repositoryAddFunction = { println("addFunction") },
        )
    }
}
