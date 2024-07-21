package com.example.iceandfire.book.di

import com.example.iceandfire.book.data.datasource.BookRemoteDataSourceImpl
import com.example.iceandfire.book.data.mapper.BookMapper
import com.example.iceandfire.book.data.repository.BookRepositoryImpl
import com.example.iceandfire.book.data.service.BookService
import com.example.iceandfire.book.domain.repository.BookRepository
import com.example.iceandfire.book.domain.usecase.GetBookByIdUseCase
import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.viewmodel.BookDetailsViewModel
import com.example.iceandfire.core.network.IceAndFireApiService
import com.example.iceandfire.book.presentation.viewmodel.BookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookModule = module {
    viewModel {
        BookViewModel(
            getBookUseCase = GetBooksUseCase(get())
        )
    }

    viewModel {
        BookDetailsViewModel(
            getBookByIdUseCase = GetBookByIdUseCase(get())
        )
    }

    factory<BookRepository> {
        BookRepositoryImpl(
            bookRemoteDataSource = BookRemoteDataSourceImpl(
                bookService = IceAndFireApiService.buildService(BookService::class.java)
            ),
            bookMapper = BookMapper()
        )
    }
}

