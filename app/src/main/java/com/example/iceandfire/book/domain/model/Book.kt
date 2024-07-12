package com.example.iceandfire.book.domain.model


data class Book(
    val authors: List<String>,
    val isbn: String,
    val name: String,
    val numberOfPages: String,
    val publisher: String,
    val released: String,
    val url: String
)