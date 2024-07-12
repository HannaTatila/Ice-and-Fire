package com.example.iceandfire.book.data.repository

import com.example.iceandfire.core.extensions.safeApiCall
import com.example.iceandfire.book.data.mapper.BookMapper
import com.example.iceandfire.book.data.service.BookService
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class BookRepositoryImpl(
    private val service: BookService,
    private val mapper: BookMapper
) : BookRepository {

    override suspend fun get(): Result<List<Book>> = withContext(Dispatchers.IO) {
        safeApiCall {
            service.getBookList().map { bookResponse -> mapper.map(bookResponse) }
        }
    }

    override suspend fun getById(url: String): Result<Book> = withContext(Dispatchers.IO) {
        val result = safeApiCall { service.getBookById(url) }
        result.map { bookResponse -> mapper.map(bookResponse) }
    }
}
