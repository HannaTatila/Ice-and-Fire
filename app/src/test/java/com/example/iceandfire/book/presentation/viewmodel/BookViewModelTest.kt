package com.example.iceandfire.book.presentation.viewmodel

import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.state.BookState
import com.example.iceandfire.stub.BookStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val URL_TEST_DEFAULT = "url_test"

@ExperimentalCoroutinesApi
class BookViewModelTest {

    private val useCase: GetBooksUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BookViewModel
    private val testDispatcher = StandardTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `on init should get book list when view model starts`() = runTest {
        // Given
        val bookList = BookStub.generateBookList()
        coEvery { useCase.invoke() } returns Result.success(bookList)

        val stateChanges = mutableListOf<BookState>()
        testScope.launch { viewModel.state.toList(stateChanges) }

        // When
        createViewModel()
        testScope.advanceUntilIdle()

        // Then
        val expectedStates = listOf(
            BookState().showLoading(),
            BookState().setBookSuccess(bookList)
        )

        Assert.assertEquals(expectedStates, stateChanges)
    }

    @Test
    fun `given failure response when init called then state shows error`() = runTest {
        // Given
        val throwable = Exception("Error")
        coEvery { useCase() } returns Result.failure(throwable)

        val stateChanges = mutableListOf<BookState>()
        testScope.launch { viewModel.state.toList(stateChanges) }

        // When
        createViewModel()
        testScope.advanceUntilIdle()

        // Then
        val expectedStates = listOf(
            BookState().showLoading(),
            BookState().setBookError(throwable)
        )
        Assert.assertEquals(expectedStates, stateChanges)
    }

    @Test
    fun `onNavigationActivated should call NavigateToBookDetails when action starts`() = runTest {
        // Given
        val bookList = BookStub.generateBookList()
        coEvery { useCase.invoke() } returns Result.success(bookList)

        // When
        createViewModel()
        viewModel.onBookItemClicked(URL_TEST_DEFAULT)

        val stateChanges = mutableListOf<BookAction>()
        testScope.launch { viewModel.action.toList(stateChanges) }
        testScope.advanceUntilIdle()

        // Then
        val expectedStates = listOf(BookAction.NavigateToBookDetails(URL_TEST_DEFAULT))
        Assert.assertEquals(expectedStates, stateChanges)
    }

    private fun createViewModel() {
        viewModel = BookViewModel(useCase)
    }
}