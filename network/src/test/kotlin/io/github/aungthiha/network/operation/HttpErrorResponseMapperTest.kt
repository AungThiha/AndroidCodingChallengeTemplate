package io.github.aungthiha.network.operation

import io.github.aungthiha.network.models.Error
import io.github.aungthiha.network.models.ErrorResponse
import io.github.aungthiha.operation.FailureType
import io.github.aungthiha.operation.Outcome
import io.github.aungthiha.operation.suspendNetworkOperation
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class HttpErrorResponseMapperTest {

    @Test
    fun `WHEN original operation succeeds, THEN returns success result without mapping`() =
        runTest {
            // Arrange
            val originalOperation = suspendNetworkOperation<String, String> { input ->
                "Success: $input"
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Success)
            assertEquals("Success: test input", (result as Outcome.Success).data)
        }

    @Test
    fun `WHEN original operation fails with HttpException and error code matches map, THEN returns mapped success result`() =
        runTest {
            // Arrange
            val errorResponse = ErrorResponse(
                errors = listOf(
                    Error(detail = "Invalid credentials", code = "invalid_credentials")
                )
            )
            val errorJson = Json.encodeToString(errorResponse)
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns errorJson

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Success)
            assertEquals("Login failed", (result as Outcome.Success).data)
        }

    @Test
    fun `WHEN original operation fails with HttpException and multiple error codes, THEN returns first matching mapped result`() =
        runTest {
            // Arrange
            val errorResponse = ErrorResponse(
                errors = listOf(
                    Error(detail = "Invalid credentials", code = "invalid_credentials"),
                    Error(detail = "Account locked", code = "account_locked")
                )
            )
            val errorJson = Json.encodeToString(errorResponse)
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns errorJson

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf(
                "invalid_credentials" to "Login failed",
                "account_locked" to "Account is locked"
            )
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Success)
            assertEquals("Login failed", (result as Outcome.Success).data)
        }

    @Test
    fun `WHEN original operation fails with HttpException and no error code matches map, THEN returns original failure`() =
        runTest {
            // Arrange
            val errorResponse = ErrorResponse(
                errors = listOf(
                    Error(detail = "Server error", code = "server_error")
                )
            )
            val errorJson = Json.encodeToString(errorResponse)
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns errorJson

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
            assertEquals(httpException, failure.e)
        }

    @Test
    fun `WHEN original operation fails with non-HttpException, THEN returns original failure`() =
        runTest {
            // Arrange
            val originalException = IOException("Network error")
            val originalOperation = suspendNetworkOperation<String, String> {
                throw originalException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.NETWORK, failure.type)
            assertEquals(originalException, failure.e)
        }

    @Test
    fun `WHEN original operation fails with HttpException but null error body, THEN returns general failure`() =
        runTest {
            // Arrange
            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns null

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
            assertTrue(failure.e is IllegalStateException)
            assertEquals("Error body is null", failure.e.message)
        }

    @Test
    fun `WHEN original operation fails with HttpException but invalid JSON, THEN returns general failure`() =
        runTest {
            // Arrange
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns "invalid json"

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
        }

    @Test
    fun `WHEN original operation fails with HttpException but null response, THEN returns general failure`() =
        runTest {
            // Arrange
            val httpException = mockk<HttpException>()
            every { httpException.response() } returns null

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
            assertTrue(failure.e is IllegalStateException)
            assertEquals("Error body is null", failure.e.message)
        }

    @Test
    fun `WHEN original operation fails with HttpException and empty error map, THEN returns original failure`() =
        runTest {
            // Arrange
            val errorResponse = ErrorResponse(
                errors = listOf(
                    Error(detail = "Invalid credentials", code = "invalid_credentials")
                )
            )
            val errorJson = Json.encodeToString(errorResponse)
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns errorJson

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = emptyMap<String, String>()
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
            assertEquals(httpException, failure.e)
        }

    @Test
    fun `WHEN original operation fails with HttpException and empty errors list, THEN returns original failure`() =
        runTest {
            // Arrange
            val errorResponse = ErrorResponse(errors = emptyList())
            val errorJson = Json.encodeToString(errorResponse)
            val responseBody = mockk<ResponseBody>()
            every { responseBody.string() } returns errorJson

            val httpResponse = mockk<Response<Any>>()
            every { httpResponse.errorBody() } returns responseBody

            val httpException = mockk<HttpException>()
            every { httpException.response() } returns httpResponse

            val originalOperation = suspendNetworkOperation<String, String> {
                throw httpException
            }
            val errorMap = mapOf("invalid_credentials" to "Login failed")
            val mappedOperation = originalOperation.withHttpErrorResponseMapper(errorMap)

            // Act
            val result = mappedOperation("test input")

            // Assert
            assertTrue(result is Outcome.Failure)
            val failure = result as Outcome.Failure
            assertEquals(FailureType.GENERAL, failure.type)
            assertEquals(httpException, failure.e)
        }
}