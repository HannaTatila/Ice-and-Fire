package com.example.iceandfire.book.data.datasource

import com.example.iceandfire.book.data.model.BookResponse
import kotlinx.coroutines.flow.Flow

interface BookLocalDataSource {

    fun getBooks(): Flow<List<BookResponse>>
    fun getBookById(bookUrl: String): Flow<BookResponse>
}