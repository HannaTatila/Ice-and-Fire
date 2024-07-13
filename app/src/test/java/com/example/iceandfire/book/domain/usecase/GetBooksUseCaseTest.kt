package com.example.iceandfire.book.domain.usecase

import com.example.iceandfire.book.domain.repository.BookRepository
import com.example.iceandfire.stub.BookStub
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetBooksUseCaseTest {

    private val mockRepository: BookRepository = mockk()
    private val getBooksUseCase = GetBooksUseCase(mockRepository)

    @Test
    fun `invoke Should return book list When repository call succeeds`() {
        // Given
        val expectedBooks = BookStub.generateBookList()
        val resultExpectedBooks = Result.success(expectedBooks)
        coEvery { mockRepository.get() } returns resultExpectedBooks

        // When
        val result = runBlocking { getBooksUseCase.invoke() }

        // Then
        assertEquals(resultExpectedBooks, result)
    }

    @Test
    fun `when repository returns error then return error`() {
        // Given
        val error = Exception("Repository error")
        val resultError: Result<Throwable> = Result.failure(error)
        coEvery { mockRepository.get() } returns Result.failure(error)

        // When
        val result = runBlocking { getBooksUseCase.invoke() }

        // Then
        assertEquals(resultError, result)
    }
}
