package io.github.aungthiha.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessListResponse<A>(
    @SerialName("results")
    val data: List<A>
)
