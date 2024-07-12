package com.example.iceandfire.book.presentation.state

import com.example.iceandfire.book.domain.model.Book

data class BookState(
    val isLoading: Boolean = true,
    val isContent: Boolean = false,
    val book: List<Book>? = null
) {

    fun showLoading() = copy(
        isLoading = true,
        isContent = false,
        book = null
    )

    fun setBookSuccess(book: List<Book>) = copy(
        isLoading = false,
        isContent = true,
        book = book
    )

    fun setBookError() = copy(
        isLoading = false,
        isContent = false,
        book = null
    )
}