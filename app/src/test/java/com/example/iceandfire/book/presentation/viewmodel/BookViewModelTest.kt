package com.example.iceandfire.book.presentation.viewmodel

import app.cash.turbine.test
import com.example.iceandfire.CoroutinesTestRule
import com.example.iceandfire.book.domain.usecase.GetBooksUseCase
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.state.BookState
import com.example.iceandfire.stub.BookStub
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

private const val URL_TEST_DEFAULT = "url_test"

@ExperimentalCoroutinesApi
class BookViewModelTest {

    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()
    private val useCase: GetBooksUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BookViewModel

    @Test
    fun `on init should get book list when view model starts`() = runTest {
        // Given
        val bookList = BookStub.generateBookList()
        coEvery { useCase.invoke() } returns flowOf(bookList)

        // When
        createViewModel()

        // Then
        viewModel.state.test {
            assertEquals(awaitItem(), BookState().showLoading())
            assertEquals(awaitItem(), BookState().setBookSuccess(bookList))
        }
    }

    @Test
    fun `onNavigationActivated should call NavigateToBookDetails when action starts`() = runTest {
        // Given
        val bookList = BookStub.generateBookList()
        coEvery { useCase.invoke() } returns flowOf(bookList)

        // When
        createViewModel()
        viewModel.onBookItemClicked(URL_TEST_DEFAULT)

        // Then
        viewModel.state.test {
            assertEquals(awaitItem(), BookState().showLoading())
            assertEquals(awaitItem(), BookState().setBookSuccess(bookList))
        }

        viewModel.action.test {
            assertEquals(awaitItem(), BookAction.NavigateToBookDetails(URL_TEST_DEFAULT))
        }
    }

    @Test
    fun `given failure response when init called then state shows error`() = runTest {
        // Given
        val messageError = "Test Error"
        coEvery { useCase() } returns flow { throw RuntimeException(messageError) }

        // When
        createViewModel()

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertFalse(loadingState.isContent)
            assertNull(loadingState.bookList)
            assertNull(loadingState.isError)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertFalse(errorState.isContent)
            assertNull(errorState.bookList)
            assertNotNull(errorState.isError)
            assertEquals(messageError, errorState.isError?.message)
        }
    }


    private fun createViewModel() {
        viewModel = BookViewModel(
            useCase,
            coroutinesTestRule.standardTestDispatcher
        )
    }
}