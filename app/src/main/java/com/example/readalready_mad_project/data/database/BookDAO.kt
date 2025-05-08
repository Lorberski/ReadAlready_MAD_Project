package com.example.readalready_mad_project.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    // Alle Bücher abrufen
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    // Ein Buch hinzufügen
    @Insert
    suspend fun insertBook(book: BookEntity)

    // Mehrere Bücher hinzufügen
    @Insert
    suspend fun insertBooks(books: List<BookEntity>)

    // Ein Buch aktualisieren
    @Update
    suspend fun updateBook(book: BookEntity)

    // Ein Buch nach ID abrufen
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): BookEntity?

    // Ein Buch löschen
    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: Long)
}
