package com.example.iceandfire.book.presentation.action


sealed class BookAction {
    data object None : BookAction()
    data class NavigateToBookDetails(val url: String) : BookAction()
}
