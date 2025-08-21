package io.github.aungthiha.operation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.IOException

class OperationTest {

    @Test
    fun `WHEN operation block executes successfully, THEN returns Success outcome`() {
        val operation = operation<String, Int> { input ->
            input.length
        }

        val result = operation("hello")

        assertTrue(result is Outcome.Success<Int>)
        assertEquals(5, (result as Outcome.Success).data)
    }

    @Test
    fun `WHEN operation block throws IOException, THEN returns Failure with GENERAL type`() {
        val operation = operation<Unit, String> {
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
    fun `WHEN operation block throws general Exception, THEN returns Failure with GENERAL type`() {
        val operation = operation<Unit, String> {
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
    fun `GIVEN custom error mapper, WHEN operation block throws Exception, THEN uses custom mapper`() {
        val customMapper: (Exception) -> Outcome<String> = { exception ->
            Outcome.Success("Custom handling: ${exception.message}")
        }
        val operation = operation<Unit, String>(mapError = customMapper) {
            throw RuntimeException("Test error")
        }

        val result = operation(Unit)

        assertTrue(result is Outcome.Success<String>)
        assertEquals("Custom handling: Test error", (result as Outcome.Success).data)
    }

    @Test
    fun `GIVEN operation with String input, WHEN invoked with different inputs, THEN processes each correctly`() {
        val operation = operation<String, String> { input ->
            "Processed: $input"
        }

        val result1 = operation("first")
        val result2 = operation("second")

        assertTrue(result1 is Outcome.Success<String>)
        assertEquals("Processed: first", (result1 as Outcome.Success).data)

        assertTrue(result2 is Outcome.Success<String>)
        assertEquals("Processed: second", (result2 as Outcome.Success).data)
    }

    @Test
    fun `GIVEN operation that processes complex data, WHEN executed, THEN handles transformation correctly`() {
        val operation = operation<List<String>, Int> { inputList ->
            inputList.sumOf { it.length }
        }

        val result = operation(listOf("hello", "world", "test"))

        assertTrue(result is Outcome.Success<Int>)
        assertEquals(14, (result as Outcome.Success).data) // 5 + 5 + 4 = 14
    }

    @Test
    fun `GIVEN operation with conditional logic, WHEN different conditions are met, THEN behaves accordingly`() {
        val operation = operation<Int, String> { input ->
            when {
                input < 0 -> throw IllegalArgumentException("Negative not allowed")
                input == 0 -> "Zero"
                input < 10 -> "Single digit: $input"
                else -> "Multiple digits: $input"
            }
        }

        // Test successful cases
        val result1 = operation(0)
        assertTrue(result1 is Outcome.Success<String>)
        assertEquals("Zero", (result1 as Outcome.Success).data)

        val result2 = operation(5)
        assertTrue(result2 is Outcome.Success<String>)
        assertEquals("Single digit: 5", (result2 as Outcome.Success).data)

        val result3 = operation(15)
        assertTrue(result3 is Outcome.Success<String>)
        assertEquals("Multiple digits: 15", (result3 as Outcome.Success).data)

        // Test failure case
        val result4 = operation(-1)
        assertTrue(result4 is Outcome.Failure<String>)
        val failure = result4 as Outcome.Failure
        assertEquals(FailureType.GENERAL, failure.type)
        assertTrue(failure.e is IllegalArgumentException)
    }

    @Test
    fun `GIVEN operation that throws different exception types, WHEN executed, THEN maps errors correctly`() {
        val ioOperation = operation<String, String> { input ->
            if (input == "io_error") throw IOException("IO problem")
            if (input == "runtime_error") throw RuntimeException("Runtime problem")
            "Success: $input"
        }

        // Test IOException mapping
        val ioResult = ioOperation("io_error")
        assertTrue(ioResult is Outcome.Failure<String>)
        assertEquals(FailureType.GENERAL, (ioResult as Outcome.Failure).type)

        // Test RuntimeException mapping
        val runtimeResult = ioOperation("runtime_error")
        assertTrue(runtimeResult is Outcome.Failure<String>)
        assertEquals(FailureType.GENERAL, (runtimeResult as Outcome.Failure).type)

        // Test success case
        val successResult = ioOperation("valid")
        assertTrue(successResult is Outcome.Success<String>)
        assertEquals("Success: valid", (successResult as Outcome.Success).data)
    }

    @Test
    fun `GIVEN operation with Unit input, WHEN invoked, THEN processes correctly`() {
        val operation = operation<Unit, String> {
            "No input needed"
        }

        val result = operation(Unit)

        assertTrue(result is Outcome.Success<String>)
        assertEquals("No input needed", (result as Outcome.Success).data)
    }
}
