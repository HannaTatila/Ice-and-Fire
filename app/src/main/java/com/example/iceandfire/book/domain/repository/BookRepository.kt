package com.example.iceandfire.book.domain.repository

import com.example.iceandfire.book.domain.model.Book

interface BookRepository {

    suspend fun get(): Result<List<Book>>
    suspend fun getById(url: String): Result<Book>
}