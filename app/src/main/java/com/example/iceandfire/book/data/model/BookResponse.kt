package com.example.iceandfire.book.data.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("authors") val authors: List<String>? = null,
    @SerializedName("isbn") val isbn: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("numberOfPages") val numberOfPages: Int? = null,
    @SerializedName("publisher") val publisher: String? = null,
    @SerializedName("released") val released: String? = null,
    @SerializedName("url") val url: String? = null
)
