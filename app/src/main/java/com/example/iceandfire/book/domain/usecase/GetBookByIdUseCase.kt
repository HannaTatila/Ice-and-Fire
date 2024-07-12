package com.example.iceandfire.book.domain.usecase

import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository

class GetBookByIdUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(url: String): Result<Book> {
        return repository.getById(url)
    }
}