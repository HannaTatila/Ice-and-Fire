package com.example.iceandfire.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.state.BookState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBookUseCase: GetBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookState())
    val state: StateFlow<BookState> get() = _state

    private val _action = MutableSharedFlow<BookAction>(extraBufferCapacity = 1)
    val action: SharedFlow<BookAction> = _action.asSharedFlow()

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            showLoading()
            delay(3000)
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
        _state.value = BookState().setBookError()
        _action.tryEmit(BookAction.ShowError(throwable.message ?: ""))
    }

    fun onBookItemClicked(url: String) {
        _action.tryEmit(BookAction.NavigateToBookDetails(url))
    }

    fun onNavigationActivated() {
        _action.tryEmit(BookAction.None)
    }

    fun onRetryClicked() {
        getBooks()
    }
}