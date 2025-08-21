package io.github.aungthiha.network.json

import kotlinx.serialization.json.Json

object JsonProvider {
    val json: Json = JsonTestDoubleProvider.json ?: Json{
        ignoreUnknownKeys = true
    }
}

object JsonTestDoubleProvider {
    var json: Json? = null
}
