package com.example.readalready_mad_project.data.repository

import android.util.Log

import com.example.readalready_mad_project.data.api.BookApiService
import com.example.readalready_mad_project.data.database.BookDao
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.mapper.BookMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

const val MAX_RESULTS = 20

class BookRepository @Inject constructor(
    private val bookDao: BookDao,
    private val bookApiService: BookApiService
){
    fun getBooksFromApi(title: String? = null, author: String? = null, isbn: String? = null):
            Flow<List<BookEntity>> = flow {
        try {
            val queryParts = mutableListOf<String>()

            author?.takeIf { it.isNotBlank() }?.let {
                queryParts.add("inauthor:$it")
            }

            title?.takeIf { it.isNotBlank() }?.let {
                queryParts.add("intitle:$it")
            }

            isbn?.takeIf { it.isNotBlank() }?.let {
                queryParts.add("isbn:$it")
            }

            val finalQuery = queryParts.joinToString("+")
            Log.d("BookRepository", "inBookRepository")

            val response = bookApiService.getBooks(
                query = finalQuery,
                maxResults = MAX_RESULTS
            )

            val booksFromApi = BookMapper.bookResponseToBookEntity(response)
            Log.d("BookRepository", "booksFromApi size: ${booksFromApi.size}")
            Log.d("BookRepository", "example ${booksFromApi[0].thumbnail}")

            emit(booksFromApi)

        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books", e)
            emit(emptyList())
        }
    }



    fun getBooksFromDb(): Flow<List<BookEntity>> {
        return bookDao.getAllBooks()
            .onEach { books ->
                if (books.isNotEmpty()) {
                    Log.d("BookRepository", "example: ${books[0]}")
                } else {
                    Log.d("BookRepository", "No books in DB")
                }
            }
    }

    suspend fun getBookById(bookId: String): BookEntity? {
        return bookDao.getBookById(bookId)
    }

    suspend fun addBookToDb(book: BookEntity){
        bookDao.insertBook(book)
    }

    suspend fun deleteBookFromDB(bookId: String){
        bookDao.deleteBook(bookId)
    }

    suspend fun toggleBookStatusInDB(book: BookEntity){
        bookDao.updateBook(book)
    }

}