package com.example.readalready_mad_project.data.mapper

import com.example.readalready_mad_project.data.database.BookEntity
import com.example.readalready_mad_project.data.model.Book

object BookMapper {

    fun toBook(bookEntity: BookEntity): Book {
        return Book(
            id = bookEntity.id,
            title = bookEntity.title,
            author = bookEntity.author,
            imageUrl = bookEntity.imageUrl,
            description = bookEntity.description,
            rating = bookEntity.rating
        )
    }


    fun toEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id ?: 0,
            title = book.title,
            author = book.author,
            imageUrl = book.imageUrl,
            description = book.description,
            rating = book.rating
        )
    }
}
