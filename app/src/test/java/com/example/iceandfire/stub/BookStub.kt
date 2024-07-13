package com.example.iceandfire.stub

import com.example.iceandfire.book.domain.model.Book

object BookStub {

    fun generateBookList(): List<Book> {
        return listOf(
            Book(
                listOf("Author 1"),
                "isbn 1",
                "Name 1",
                "Number 1",
                "Publisher 1",
                "Released 1",
                "url 1"
            ),
            Book(
                listOf("Author 2"),
                "isbn 2",
                "Name 2",
                "Number 2",
                "Publisher 2",
                "Released 2",
                "url 2"
            )
        )

    }

    fun generateBook(): Book {
        return Book(
            listOf("Author 1"),
            "isbn 1",
            "Name 1",
            "Number 1",
            "Publisher 1",
            "Released 1",
            "url 1"
        )
    }
}