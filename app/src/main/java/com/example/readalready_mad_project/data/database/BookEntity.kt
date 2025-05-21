package com.example.readalready_mad_project.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.readalready_mad_project.data.api.ImageLinks

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: List<String>?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val smallThumbnail: String?,
    val thumbnail: String?
)
