package com.example.iceandfire.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.domain.usecase.GetBookByIdUseCase
import com.example.iceandfire.book.presentation.state.BookDetailsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsState())
    val state: StateFlow<BookDetailsState> get() = _state

    private fun getBookById(url: String) {
        viewModelScope.launch {
            getBookByIdUseCase(url)
                .flowOn(dispatcher)
                .onStart { showLoading() }
                .catch { throwable -> handleError(throwable) }
                .collect { book -> handleSuccess(book) }
        }
    }

    private fun showLoading() {
        _state.value = BookDetailsState().showLoading()
    }

    private fun handleSuccess(book: Book) {
        _state.value = BookDetailsState().setBookSuccess(book)
    }

    private fun handleError(throwable: Throwable) {
        _state.value = BookDetailsState().setBookError(throwable)
    }

    fun onScreenInitialized(url: String) {
        getBookById(url)
    }

    fun onRetryClicked(url: String) {
        getBookById(url)
    }
}