package io.github.aungthiha.operation

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.IOException

class SuspendNetworkOperationTest {

    @Test
    fun `WHEN suspendNetworkOperation block executes successfully, THEN returns Success outcome`() =
        runTest {
            val operation = suspendNetworkOperation<String, Int> { input ->
                input.length
            }

            val result = operation("hello")

            assertTrue(result is Outcome.Success<Int>)
            assertEquals(5, (result as Outcome.Success).data)
        }

    @Test
    fun `WHEN suspendNetworkOperation block throws IOException, THEN returns Failure with NETWORK type`() =
        runTest {
            val operation = suspendNetworkOperation<Unit, String> {
                throw IOException("Network error")
            }

            val result = operation(Unit)

            assertTrue(result is Outcome.Failure<String>)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.NETWORK, failure.type)
            assertTrue(failure.e is IOException)
            assertEquals("Network error", failure.e.message)
        }

    @Test
    fun `WHEN suspendNetworkOperation block throws general Exception, THEN returns Failure with GENERAL type`() =
        runTest {
            val operation = suspendNetworkOperation<Unit, String> {
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
    fun `GIVEN Unit input network operation, WHEN invoke() extension is called, THEN executes successfully`() =
        runTest {
            val operation = suspendNetworkOperation<Unit, String> { "Network Data" }

            val result = operation.invoke()

            assertTrue(result is Outcome.Success<String>)
            assertEquals("Network Data", (result as Outcome.Success).data)
        }

    @Test
    fun `GIVEN successful Unit input network operation, WHEN getOrNull() extension is called, THEN returns data`() =
        runTest {
            val operation = suspendNetworkOperation<Unit, String> { "Network Data" }

            val result = operation.getOrNull()

            assertEquals("Network Data", result)
        }

    @Test
    fun `GIVEN failing Unit input network operation with IOException, WHEN getOrNull() extension is called, THEN returns null`() =
        runTest {
            val operation = suspendNetworkOperation<Unit, String> {
                throw IOException("Network timeout")
            }

            val result = operation.getOrNull()

            assertNull(result)
        }

    @Test
    fun `GIVEN successful network operation with input, WHEN getOrNull(input) extension is called, THEN returns data`() =
        runTest {
            val operation = suspendNetworkOperation<String, String> { input ->
                "Fetched: $input"
            }

            val result = operation.getOrNull("user_data")

            assertEquals("Fetched: user_data", result)
        }

    @Test
    fun `GIVEN failing network operation with input, WHEN getOrNull(input) extension is called, THEN returns null`() =
        runTest {
            val operation = suspendNetworkOperation<String, String> {
                throw IOException("Connection failed")
            }

            val result = operation.getOrNull("user_data")

            assertNull(result)
        }

    @Test
    fun `GIVEN network operation that throws different exception types, WHEN executed, THEN maps errors correctly`() =
        runTest {
            val networkOperation = suspendNetworkOperation<String, String> { input ->
                when (input) {
                    "io_error" -> throw IOException("Network problem")
                    "runtime_error" -> throw RuntimeException("Runtime problem")
                    else -> "Success: $input"
                }
            }

            // Test IOException mapping to NETWORK type
            val ioResult = networkOperation("io_error")
            assertTrue(ioResult is Outcome.Failure<String>)
            assertEquals(FailureType.NETWORK, (ioResult as Outcome.Failure).type)

            // Test RuntimeException mapping to GENERAL type
            val runtimeResult = networkOperation("runtime_error")
            assertTrue(runtimeResult is Outcome.Failure<String>)
            assertEquals(FailureType.GENERAL, (runtimeResult as Outcome.Failure).type)

            // Test success case
            val successResult = networkOperation("valid")
            assertTrue(successResult is Outcome.Success<String>)
            assertEquals("Success: valid", (successResult as Outcome.Success).data)
        }
}
