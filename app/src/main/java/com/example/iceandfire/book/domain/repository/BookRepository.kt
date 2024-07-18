package com.example.iceandfire.book.domain.repository

import com.example.iceandfire.book.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(): Flow<List<Book>>
    fun getBookById(url: String): Flow<Book>
}