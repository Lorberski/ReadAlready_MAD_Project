package com.example.readalready_mad_project.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    // get all books
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    // add book
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntity)

    // add books
    @Insert
    suspend fun insertBooks(books: List<BookEntity>)

    // update one book
    @Update
    suspend fun updateBook(book: BookEntity)

    // get book from id
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): BookEntity?

    // delete book
    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: String)

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    suspend fun getBookById(bookId: String): BookEntity?



//    @Query("SELECT * FROM BookEntity")
//    fun getAllBooksFlow(): Flow<List<BookEntity>>
}
