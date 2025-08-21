package io.github.aungthiha.okhttp3.testdoubles

import io.github.aungthiha.okhttp3.toResponseJsonBody
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException

fun Request.create401Response(
    priorResponse: Response? = null
): Response = Response.Builder()
    .request(this)
    .protocol(okhttp3.Protocol.HTTP_1_1)
    .code(401)
    .message("Unauthorized")
    .apply {
        priorResponse?.let(::priorResponse)
    }
    .build()

fun createHttpException500(): HttpException {
    val errorResponse = "{\"errors\":[{\"detail\":\"Something went wrong\"}]}"
    return HttpException(
        retrofit2.Response.error<Any>(
            500,
            errorResponse.toResponseJsonBody()
        )
    )
}

fun createHttpException401Unauthorized(): HttpException {
    val unauthorizedJson = """
        {
          "errors": [
            {
              "detail": "Your session has expired. Please log in again.",
              "code": "unauthorized"
            }
          ]
        }
    """.trimIndent()
    return HttpException(
        retrofit2.Response.error<Any>(
            401,
            unauthorizedJson.toResponseJsonBody()
        )
    )
}

fun createHttpException401InvalidCredentials(): HttpException {
    val unauthorizedJson = """
        {
          "errors": [
            {
              "detail": "Your email or password is incorrect. Please try again.",
              "code": "invalid_email_or_password"
            }
          ]
        }
    """.trimIndent()
    return HttpException(
        retrofit2.Response.error<Any>(
            401,
            unauthorizedJson.toResponseJsonBody()
        )
    )
}

fun createHttpException401InvalidRefreshToken(): HttpException {
    val unauthorizedJson = """
        {
          "errors": [
            {
              "detail": "Your refresh token is invalid or expired.",
              "code": "invalid_refresh_token"
            }
          ]
        }
    """.trimIndent()
    return HttpException(
        retrofit2.Response.error<Any>(
            401,
            unauthorizedJson.toResponseJsonBody()
        )
    )
}
