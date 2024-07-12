package com.example.iceandfire.core.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate) ?: "-"

    return outputFormat.format(date)
}