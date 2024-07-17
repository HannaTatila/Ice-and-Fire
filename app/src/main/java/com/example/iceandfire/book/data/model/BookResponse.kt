package com.example.iceandfire.book.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    @SerialName("authors") val authors: List<String>?,
    @SerialName("isbn") val isbn: String?,
    @SerialName("name") val name: String?,
    @SerialName("numberOfPages") val numberOfPages: Int?,
    @SerialName("publisher") val publisher: String?,
    @SerialName("released") val released: String?,
    @SerialName("url") val url: String?
)
