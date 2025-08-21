package io.github.aungthiha.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("errors")
    val errors: List<Error>
)

@Serializable
data class Error(
    @SerialName("detail")
    val detail: String,
    @SerialName("code")
    val code: String
)
