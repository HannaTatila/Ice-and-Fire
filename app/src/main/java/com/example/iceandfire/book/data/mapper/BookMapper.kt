package com.example.iceandfire.book.data.mapper

import com.example.iceandfire.book.data.model.BookResponse
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.core.util.formatDate

class BookMapper {

    fun map(source: List<BookResponse>): List<Book> {
        return source.map { bookResponse ->
            Book(
                authors = bookResponse.authors.orEmpty(),
                isbn = bookResponse.isbn.orEmpty(),
                name = bookResponse.name.orEmpty(),
                numberOfPages = (bookResponse.numberOfPages ?: String()).toString(),
                publisher = bookResponse.publisher.orEmpty(),
                released = formatDate(bookResponse.released.orEmpty()),
                url = bookResponse.url.orEmpty()
            )
        }
    }

    fun map(source: BookResponse): Book {
        return Book(
            authors = source.authors.orEmpty(),
            isbn = source.isbn.orEmpty(),
            name = source.name.orEmpty(),
            numberOfPages = (source.numberOfPages ?: String()).toString(),
            publisher = source.publisher.orEmpty(),
            released = formatDate(source.released.orEmpty()),
            url = source.url.orEmpty()
        )
    }
}