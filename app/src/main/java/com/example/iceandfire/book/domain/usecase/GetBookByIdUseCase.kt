package com.example.iceandfire.book.domain.usecase

import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBookByIdUseCase(private val repository: BookRepository) {

    operator fun invoke(url: String): Flow<Book> {
        return repository.getBookById(url)
    }
}