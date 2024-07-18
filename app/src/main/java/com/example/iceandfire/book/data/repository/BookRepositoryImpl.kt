package com.example.iceandfire.book.data.repository

import com.example.iceandfire.book.data.datasource.BookLocalDataSource
import com.example.iceandfire.book.data.mapper.BookMapper
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val localDataSource: BookLocalDataSource,
    private val mapper: BookMapper
) : BookRepository {

    override fun getBooks(): Flow<List<Book>> {
        return localDataSource.getBooks()
            .map { bookResponse -> mapper.map(bookResponse) }
    }

    override fun getBookById(url: String): Flow<Book> {
        return localDataSource.getBookById(url)
            .map { bookResponse -> mapper.map(bookResponse) }
    }
}
