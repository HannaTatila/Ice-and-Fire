package com.example.iceandfire.book.data.repository

import com.example.iceandfire.book.data.datasource.BookRemoteDataSource
import com.example.iceandfire.book.data.mapper.BookMapper
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val bookMapper: BookMapper
) : BookRepository {

    override fun getBooks(): Flow<List<Book>> {
        return bookRemoteDataSource.getBooks()
            .map { bookResponse -> bookMapper.toBookList(bookResponse) }
    }

    override fun getBookById(url: String): Flow<Book> {
        return bookRemoteDataSource.getBookById(url)
            .map { bookResponse -> bookMapper.toBook(bookResponse) }
    }
}
