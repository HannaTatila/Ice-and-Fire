package com.example.iceandfire.book.presentation.viewmodel

import app.cash.turbine.test
import com.example.iceandfire.CoroutinesTestRule
import com.example.iceandfire.book.domain.usecase.GetBookByIdUseCase
import com.example.iceandfire.book.presentation.state.BookDetailsState
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
class BookDetailsViewModelTest {

    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()
    private val useCase: GetBookByIdUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BookDetailsViewModel

    @Test
    fun `onScreenInitialized should get book with specific url`() = runTest {
        // Given
        val book = BookStub.generateBook()
        coEvery { useCase.invoke(URL_TEST_DEFAULT) } returns flowOf(book)

        // When
        createViewModel()
        viewModel.onScreenInitialized(URL_TEST_DEFAULT)

        // Then
        viewModel.state.test {
            assertEquals(awaitItem(), BookDetailsState().showLoading())
            assertEquals(awaitItem(), BookDetailsState().setBookSuccess(book))
        }
    }


    @Test
    fun `given failure response when getBookById called then state shows error`() = runTest {
        // Given
        val messageError = "Test Error"
        coEvery { useCase(any()) } returns flow { throw RuntimeException(messageError) }

        // When
        createViewModel()
        viewModel.onScreenInitialized(URL_TEST_DEFAULT)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertFalse(loadingState.isContent)
            assertNull(loadingState.book)
            assertNull(loadingState.isError)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertFalse(errorState.isContent)
            assertNull(errorState.book)
            assertNotNull(errorState.isError)
            assertEquals(messageError, errorState.isError?.message)
        }
    }

    private fun createViewModel() {
        viewModel = BookDetailsViewModel(
            useCase,
            coroutinesTestRule.standardTestDispatcher
        )
    }
}