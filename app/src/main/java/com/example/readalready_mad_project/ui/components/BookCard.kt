package com.example.readalready_mad_project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.ui.theme.ReadAlreadyTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue



@Composable
fun BookCard(book: BookEntity) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .heightIn(min = 100.dp, max = if (expanded) 3000.dp else 200.dp)
            .clickable { expanded = !expanded }, // Toggle beim Tippen
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.shape
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            BookImage(book.thumbnail)

            Column(modifier = Modifier.align(Alignment.Top)) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Author: ${book.authors?.joinToString(", ")}",
                    fontSize = 14.sp
                )
                Text(
                    text = if (book.alreadyRead) "Status: Already read" else "Status: Not read",
                    fontSize = 13.sp
                )


                if (expanded) {
                    Text(
                        text = "Publisher: ${book.publisher}",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Published: ${book.publishedDate}",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Pages: ${book.pageCount}",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Rating: ${book.averageRating} (${book.ratingsCount} ratings)",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Description: ${book.description}",
                        fontSize = 13.sp,
                    )
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
        BookCard(BookEntity(
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
        ))
    }
}
