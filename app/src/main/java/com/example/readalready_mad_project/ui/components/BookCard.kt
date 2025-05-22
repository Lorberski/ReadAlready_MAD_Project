package com.example.readalready_mad_project.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readalready_mad_project.data.database.BookEntity

@Composable
fun BookCard(book: BookEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.shape
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Author: ${book.authors}", fontSize = 14.sp)
            Text(
                text = if (book.alreadyRead) "Status: Already read" else "Status: Not read",
                fontSize = 13.sp
            )
        }
    }
}
