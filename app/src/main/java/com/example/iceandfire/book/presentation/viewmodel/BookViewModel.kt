package com.example.iceandfire.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.state.BookState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBookUseCase: GetBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookState())
    val state: StateFlow<BookState> get() = _state

    private val _action = MutableStateFlow<BookAction>(BookAction.None)
    val action: StateFlow<BookAction> = _action

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            showLoading()
            getBookUseCase()
                .onSuccess { bookList -> handleSuccess(bookList) }
                .onFailure { throwable -> handleError(throwable) }
        }
    }

    private fun showLoading() {
        _state.value = BookState().showLoading()
    }

    private fun handleSuccess(bookList: List<Book>) {
        _state.value = BookState().setBookSuccess(bookList)
    }

    private fun handleError(throwable: Throwable) {
        _state.value = BookState().setBookError(throwable)
    }

    fun onBookItemClicked(url: String) {
        _action.value = BookAction.NavigateToBookDetails(url)
    }

    fun onNavigationActivated() {
        _action.value = BookAction.None
    }

    fun onRetryClicked() {
        getBooks()
    }
}