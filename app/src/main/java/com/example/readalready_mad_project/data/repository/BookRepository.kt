package com.example.readalready_mad_project.data.repository

import android.util.Log
import com.example.readalready_mad_project.BuildConfig

import com.example.readalready_mad_project.data.api.GoogleBookApi
import com.example.readalready_mad_project.data.api.GoogleBookApiService
import com.example.readalready_mad_project.data.api.NYTBookApi
import com.example.readalready_mad_project.data.api.NYTBookApiService

import com.example.readalready_mad_project.data.database.BookDao
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.mapper.BookMapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


const val MAX_RESULTS = 20

class BookRepository @Inject constructor(
    private val bookDao: BookDao,
    @GoogleBookApi private val bookApiService: GoogleBookApiService,
    @NYTBookApi private val nytBookApiService: NYTBookApiService
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


    fun getTrendingBooks(): Flow<List<BookEntity>> = flow {
        try {
            val nytApiBooksResponse = nytBookApiService.getTrendingBooks(BuildConfig.MY_API_KEY)
            val nytBooks = nytApiBooksResponse.results?.lists?.flatMap { it.books!! } ?: emptyList()
            val trendingTitles = nytBooks.mapNotNull { it.title }.take(10)
            val booksFromGoogle = mutableListOf<BookEntity>()

            for (title in trendingTitles) {
                val response = bookApiService.getBooks(
                    query = title,
                    maxResults = 1
                )
                val bookEntities = BookMapper.bookResponseToBookEntity(response)
                booksFromGoogle.addAll(bookEntities)
            }

            emit(booksFromGoogle)

        }catch (e: Exception){
            Log.e("BookRepository", "Error fetching books", e)
            emit(emptyList())
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

    suspend fun updateBookInDb(book: BookEntity){
        bookDao.updateBook(book)
    }

}