package com.example.iceandfire.book.presentation.state

import com.example.iceandfire.book.domain.model.Book

data class BookState(
    val isLoading: Boolean = true,
    val isContent: Boolean = false,
    val bookList: List<Book>? = null,
    val isError: Throwable? = null
) {

    fun showLoading() = copy(
        isLoading = true,
        isContent = false,
        bookList = null,
        isError = null
    )

    fun setBookSuccess(book: List<Book>) = copy(
        isLoading = false,
        isContent = true,
        bookList = book,
        isError = null
    )

    fun setBookError(throwable: Throwable) = copy(
        isLoading = false,
        isContent = false,
        bookList = null,
        isError = throwable
    )
}