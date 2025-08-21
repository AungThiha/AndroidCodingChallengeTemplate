package io.github.aungthiha.operation

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class SuspendOperationTest {

    @Test
    fun `WHEN suspendOperation block executes successfully, THEN returns Success outcome`() = runTest {
        val operation = suspendOperation<String, Int> { input ->
            input.length
        }

        val result = operation("hello")

        assertTrue(result is Outcome.Success<Int>)
        assertEquals(5, (result as Outcome.Success).data)
    }

    @Test
    fun `WHEN suspendOperation block throws IOException, THEN returns Failure with GENERAL type`() = runTest {
        val operation = suspendOperation<Unit, String> {
            throw IOException("IO error")
        }

        val result = operation(Unit)

        assertTrue(result is Outcome.Failure<String>)
        val failure = result as Outcome.Failure
        assertEquals(FailureType.GENERAL, failure.type)
        assertTrue(failure.e is IOException)
        assertEquals("IO error", failure.e.message)
    }

    @Test
    fun `WHEN suspendOperation block throws general Exception, THEN returns Failure with GENERAL type`() = runTest {
        val operation = suspendOperation<Unit, String> {
            throw IllegalArgumentException("Invalid argument")
        }

        val result = operation(Unit)

        assertTrue(result is Outcome.Failure<String>)
        val failure = result as Outcome.Failure
        assertEquals(FailureType.GENERAL, failure.type)
        assertTrue(failure.e is IllegalArgumentException)
        assertEquals("Invalid argument", failure.e.message)
    }

    @Test
    fun `GIVEN custom error mapper, WHEN suspendOperation block throws Exception, THEN uses custom mapper`() = runTest {
        val customMapper: (Exception) -> Outcome<String> = { exception ->
            Outcome.Success("Custom handling: ${exception.message}")
        }
        val operation = suspendOperation<Unit, String>(mapError = customMapper) {
            throw RuntimeException("Test error")
        }

        val result = operation(Unit)

        assertTrue(result is Outcome.Success<String>)
        assertEquals("Custom handling: Test error", (result as Outcome.Success).data)
    }

    @Test
    fun `GIVEN Unit input operation, WHEN invoke() extension is called, THEN executes successfully`() = runTest {
        val operation = suspendOperation<Unit, String> { "Hello World" }

        val result = operation.invoke()

        assertTrue(result is Outcome.Success<String>)
        assertEquals("Hello World", (result as Outcome.Success).data)
    }

    @Test
    fun `GIVEN successful Unit input operation, WHEN getOrNull() extension is called, THEN returns data`() = runTest {
        val operation = suspendOperation<Unit, String> { "Hello World" }

        val result = operation.getOrNull()

        assertEquals("Hello World", result)
    }

    @Test
    fun `GIVEN failing Unit input operation, WHEN getOrNull() extension is called, THEN returns null`() = runTest {
        val operation = suspendOperation<Unit, String> {
            throw IOException("IO error")
        }

        val result = operation.getOrNull()

        assertNull(result)
    }

    @Test
    fun `GIVEN successful operation with input, WHEN getOrNull(input) extension is called, THEN returns data`() = runTest {
        val operation = suspendOperation<String, Int> { input ->
            input.length * 2
        }

        val result = operation.getOrNull("test")

        assertEquals(8, result)
    }

    @Test
    fun `GIVEN failing operation with input, WHEN getOrNull(input) extension is called, THEN returns null`() = runTest {
        val operation = suspendOperation<String, Int> {
            throw IllegalStateException("Processing error")
        }

        val result = operation.getOrNull("test")

        assertNull(result)
    }

    @Test
    fun `GIVEN successful outcome, WHEN rethrowIfFailure() is called, THEN does not throw`() = runTest {
        val outcome = Outcome.Success("data")

        // Should not throw
        outcome.rethrowIfFailure()
    }

    @Test
    fun `GIVEN failure outcome, WHEN rethrowIfFailure() is called, THEN throws original exception`() = runTest {
        val originalException = IllegalArgumentException("Original error")
        val outcome = Outcome.Failure<String>(FailureType.GENERAL, originalException)

        val thrownException = assertThrows<IllegalArgumentException> {
            outcome.rethrowIfFailure()
        }

        assertEquals(originalException, thrownException)
        assertEquals("Original error", thrownException.message)
    }
}
