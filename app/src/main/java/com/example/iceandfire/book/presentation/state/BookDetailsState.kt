package com.example.iceandfire.book.presentation.state

import com.example.iceandfire.book.domain.model.Book

data class BookDetailsState(
    val isLoading: Boolean = true,
    val isContent: Boolean = false,
    val book: Book? = null
) {

    fun showLoading() = copy(
        isLoading = true,
        isContent = false,
        book = null
    )

    fun setBookSuccess(book: Book) = copy(
        isLoading = false,
        isContent = true,
        book = book
    )
}