package io.github.aungthiha.myfeature.data.remote

import io.github.aungthiha.myfeature.data.remote.dtos.PokemonDTO
import io.github.aungthiha.network.models.SuccessListResponse
import retrofit2.http.GET

interface MyfeatureDataSource {
    @GET("pokemon?limit=10")
    suspend fun getPokemonList(): SuccessListResponse<PokemonDTO>
}
