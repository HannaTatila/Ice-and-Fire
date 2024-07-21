package com.example.iceandfire.book.data.mapper

import com.example.iceandfire.book.data.model.BookResponse
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.core.util.formatDate

class BookMapper {

    fun toBookList(source: List<BookResponse>): List<Book> {
        return source.map { bookResponse ->
            toBook(bookResponse)
        }
    }

    fun toBook(source: BookResponse): Book {
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