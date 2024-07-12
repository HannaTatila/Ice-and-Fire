package com.example.iceandfire.book.domain.usecase

import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository

class GetBooksUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(): Result<List<Book>> {
        return repository.get()
    }
}