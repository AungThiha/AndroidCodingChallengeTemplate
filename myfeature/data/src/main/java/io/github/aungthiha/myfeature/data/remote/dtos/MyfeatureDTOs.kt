package io.github.aungthiha.myfeature.data.remote.dtos

import io.github.aungthiha.myfeature.domain.models.Pokemon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDTO(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)

fun PokemonDTO.toPokemon(): Pokemon {
    return Pokemon(name = name, url = url)
}
