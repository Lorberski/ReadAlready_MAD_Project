package com.example.readalready_mad_project.data.model

data class Book(
    val id: Long? = null,  // optional, wird bei Bedarf gesetzt (z. B. bei einem bestehenden Buch)
    val title: String,
    val author: String,
    val imageUrl: String,
    val description: String,
    val rating: Float
)
