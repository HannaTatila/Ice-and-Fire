package com.example.iceandfire.book.domain.usecase

import app.cash.turbine.test
import com.example.iceandfire.book.domain.repository.BookRepository
import com.example.iceandfire.stub.BookStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetBooksUseCaseTest {

    private val mockRepository: BookRepository = mockk()
    private val getBooksUseCase = GetBooksUseCase(mockRepository)

    @Test
    fun `invoke Should return book list When repository call succeeds`() = runTest {
        // Given
        val expectedBooks = BookStub.generateBookList()
        coEvery { mockRepository.getBooks() } returns flowOf(expectedBooks)

        // When
        val result = getBooksUseCase.invoke()

        // Then
        result.test {
            assertEquals(expectedBooks, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when repository returns error then return error`() = runTest {
        // Given
        val messageError = "Test Error"
        coEvery { mockRepository.getBooks() } returns flow { throw RuntimeException(messageError) }

        // When
        val result = getBooksUseCase.invoke()

        // Then
        result.test {
            assertEquals(messageError, awaitError().message)
            awaitError()
        }
    }
}
