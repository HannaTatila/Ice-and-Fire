package com.example.iceandfire.book.data.service

import com.example.iceandfire.book.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface BookService {

    @GET("books")
    suspend fun getBookList(): List<BookResponse>

    @GET
    suspend fun getBookById(@Url bookUrl: String): BookResponse
}