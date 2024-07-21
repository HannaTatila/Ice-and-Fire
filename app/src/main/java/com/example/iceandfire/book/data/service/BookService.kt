package com.example.iceandfire.book.data.service

import com.example.iceandfire.book.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BookService {

    @GET("books")
    suspend fun getBookList(
        @Query("pageSize") pageSize: String = "15"
    ): List<BookResponse>

    @GET
    suspend fun getBookById(@Url bookUrl: String): BookResponse
}