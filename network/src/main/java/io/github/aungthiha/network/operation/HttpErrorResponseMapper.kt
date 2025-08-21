package io.github.aungthiha.network.operation

import io.github.aungthiha.network.json.JsonProvider
import io.github.aungthiha.network.models.ErrorResponse
import io.github.aungthiha.operation.FailureType
import io.github.aungthiha.operation.Outcome
import io.github.aungthiha.operation.SuspendOperation
import retrofit2.HttpException

fun HttpException.parseHttpError(): ErrorResponse {
    val errorBody = response()?.errorBody()?.string()
        ?: throw IllegalStateException("Error body is null")
    return JsonProvider.json.decodeFromString<ErrorResponse>(errorBody)
}

fun <I, O> SuspendOperation<I, O>.withHttpErrorResponseMapper(
    map: Map<String, O>
): SuspendOperation<I, O> = SuspendOperation { input ->
    val result = this.invoke(input)

    if (result is Outcome.Failure) {
        val exception = result.e as? HttpException ?: return@SuspendOperation result

        try {
            val errorResponse = exception.parseHttpError()
            for (error in errorResponse.errors) {
                map[error.code]?.let { mappedValue ->
                    return@SuspendOperation Outcome.Success(mappedValue)
                }
            }
            return@SuspendOperation result
        } catch (e: Exception) {
            return@SuspendOperation Outcome.Failure(FailureType.GENERAL, e)
        }
    }
    result
}
