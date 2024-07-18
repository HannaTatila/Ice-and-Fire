package com.example.iceandfire.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.state.BookState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBookUseCase: GetBooksUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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
            getBookUseCase()
                .flowOn(dispatcher)
                .onStart { showLoading() }
                .catch { throwable -> handleError(throwable) }
                .collect { bookList -> handleSuccess(bookList) }
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