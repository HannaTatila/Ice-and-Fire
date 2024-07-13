package com.example.iceandfire.book.presentation.viewmodel

import com.example.iceandfire.book.domain.usecase.GetBookByIdUseCase
import com.example.iceandfire.book.presentation.state.BookDetailsState
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
class BookDetailsViewModelTest {

    private val useCase: GetBookByIdUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BookDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BookDetailsViewModel(useCase)
    }

    @Test
    fun `onScreenInitialized should get book with specific url`() = runTest {
        // Given
        val book = BookStub.generateBook()
        coEvery { useCase.invoke(URL_TEST_DEFAULT) } returns Result.success(book)

        val stateChanges = mutableListOf<BookDetailsState>()
        testScope.launch { viewModel.state.toList(stateChanges) }

        // When
        viewModel.onScreenInitialized(URL_TEST_DEFAULT)
        testScope.advanceUntilIdle()

        // Then
        val expectedStates = listOf(
            BookDetailsState().showLoading(),
            BookDetailsState().setBookSuccess(book)
        )

        Assert.assertEquals(expectedStates, stateChanges)
    }


    @Test
    fun `given failure response when getBookById called then state shows error`() = runTest {
        // Given
        val throwable = Exception("Error")
        coEvery { useCase(any()) } returns Result.failure(throwable)

        val stateChanges = mutableListOf<BookDetailsState>()
        testScope.launch { viewModel.state.toList(stateChanges) }

        // When
        viewModel.onScreenInitialized(URL_TEST_DEFAULT)
        testScope.advanceUntilIdle()

        // Then
        val expectedStates = listOf(
            BookDetailsState().showLoading(),
            BookDetailsState().setBookError(throwable)
        )
        Assert.assertEquals(expectedStates, stateChanges)
    }
}