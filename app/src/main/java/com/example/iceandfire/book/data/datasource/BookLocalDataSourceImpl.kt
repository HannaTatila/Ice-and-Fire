package com.example.iceandfire.book.data.datasource

import com.example.iceandfire.book.data.model.BookResponse
import com.example.iceandfire.book.data.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookLocalDataSourceImpl(
    private val bookService: BookService
) : BookLocalDataSource {

    override fun getBooks(): Flow<List<BookResponse>> {
        return flow { emit(bookService.getBookList()) }
    }

    override fun getBookById(bookUrl: String): Flow<BookResponse> {
        return flow { emit(bookService.getBookById(bookUrl)) }
    }
}