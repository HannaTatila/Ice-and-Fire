package com.example.iceandfire.book.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UrlBookArgs(
    val url: String
) : Parcelable