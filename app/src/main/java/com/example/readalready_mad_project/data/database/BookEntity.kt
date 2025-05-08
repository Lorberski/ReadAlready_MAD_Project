package com.example.readalready_mad_project.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")  // Der Name der Tabelle in der Datenbank
data class BookEntity(
    @PrimaryKey(autoGenerate = true)  // Automatisch eine ID generieren
    val id: Long = 0,  // Primärschlüssel (wird automatisch generiert)
    val title: String,  // Titel des Buches
    val author: String,  // Autor des Buches
    val imageUrl: String,  // URL des Buchbildes
    val description: String,  // Beschreibung des Buches
    val rating: Float  // Bewertung des Buches (z.B. von 1 bis 5)
)
