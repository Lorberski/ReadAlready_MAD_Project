package com.example.readalready_mad_project.data.mapper

import com.example.readalready_mad_project.data.api.BookResponse
import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.model.Book

object BookMapper {
    
    fun bookResponseToBookEntity(bookResponse: BookResponse): List<BookEntity>{
        val items = bookResponse.items ?: return emptyList()
        
        return items.map{
            item -> BookEntity(
            id = ((item.id ?: 0).toString()),
            title = item.volumeInfo?.title ?: "No Title",
            authors = item.volumeInfo?.authors ?: emptyList(),
            publisher = item.volumeInfo?.publisher ?: "No Data",
            publishedDate = item.volumeInfo?.publishedDate ?: "No Data",
            description = item.volumeInfo?.description ?: "No Data",
            pageCount = item.volumeInfo?.pageCount ?: 0,
            categories = item.volumeInfo?.categories ?: emptyList(),
            averageRating = item.volumeInfo?.averageRating ?: 0.0,
            ratingsCount = item.volumeInfo?.ratingsCount ?: 0,
            smallThumbnail = item.volumeInfo?.imageLinks?.smallThumbnail ?: "No Data",
            thumbnail = item.volumeInfo?.imageLinks?.thumbnail ?: "No Data",
        )
        }
        }
    }

//    fun toBook(bookEntity: BookEntity): Book {
//        return Book(
//            id = bookEntity.id,
//            title = bookEntity.title,
//            author = bookEntity.author,
//            imageUrl = bookEntity.imageUrl,
//            description = bookEntity.description,
//            rating = bookEntity.rating
//        )
//    }
//
//
//    fun toEntity(book: Book): BookEntity {
//        return BookEntity(
//            id = book.id ?: 0,
//            title = book.title,
//            author = book.author,
//            imageUrl = book.imageUrl,
//            description = book.description,
//            rating = book.rating
//        )
//    }
//}
