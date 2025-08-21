package io.github.aungthiha.myfeature.testdoubles

import io.github.aungthiha.myfeature.data.remote.MyfeatureDataSource
import io.github.aungthiha.myfeature.data.remote.dtos.PokemonDTO
import io.github.aungthiha.network.models.SuccessListResponse
import io.github.aungthiha.okhttp3.testdoubles.createHttpException401Unauthorized
import io.github.aungthiha.okhttp3.testdoubles.createHttpException500
import kotlinx.coroutines.delay
import java.io.IOException

class StubMyfeatureDataSource : MyfeatureDataSource {

    private val getPokemonListStubs =
        mutableListOf<suspend () -> SuccessListResponse<PokemonDTO>>()

    override suspend fun getPokemonList(): SuccessListResponse<PokemonDTO> {
        if (getPokemonListStubs.isEmpty()) {
            throw Throwable("No stubbed response provided for getPokemonList")
        }
        val stub = getPokemonListStubs.removeAt(0)
        return stub()
    }

    fun stubGetPokemonListSuccess() {
        getPokemonListStubs.add {
            FakePokemonListSuccessResponse
        }
    }

    fun stubGetPokemonListFailureWithIOException() {
        getPokemonListStubs.add {
            throw IOException("Simulated IO failure")
        }
    }

    fun stubGetPokemonListFailureWithHttpExceptionUnknownError() {
        getPokemonListStubs.add {
            throw createHttpException500()
        }
    }

    fun stubGetPokemonListFailureWithHttpExceptionUnauthorized() {
        getPokemonListStubs.add {
            throw createHttpException401Unauthorized()
        }
    }

    fun stubGetPokemonListFailureWithGeneralException() {
        getPokemonListStubs.add {
            throw IllegalStateException("Generic exception")
        }
    }

    fun stubGetPokemonListSuccessWithDelay() {
        getPokemonListStubs.add {
            delay(10) // for testing loading.
            FakePokemonListSuccessResponse
        }
    }

    fun stubGetPokemonListSuccessEmpty() {
        getPokemonListStubs.add {
            FakePokemonListEmptyResponse
        }
    }
}

// Fake data responses
val FakePokemonListSuccessResponse = SuccessListResponse(
    data = listOf(
        PokemonDTO(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        ),
        PokemonDTO(
            name = "ivysaur",
            url = "https://pokeapi.co/api/v2/pokemon/2/"
        ),
        PokemonDTO(
            name = "venusaur",
            url = "https://pokeapi.co/api/v2/pokemon/3/"
        ),
        PokemonDTO(
            name = "charmander",
            url = "https://pokeapi.co/api/v2/pokemon/4/"
        ),
        PokemonDTO(
            name = "charmeleon",
            url = "https://pokeapi.co/api/v2/pokemon/5/"
        ),
        PokemonDTO(
            name = "charizard",
            url = "https://pokeapi.co/api/v2/pokemon/6/"
        ),
        PokemonDTO(
            name = "squirtle",
            url = "https://pokeapi.co/api/v2/pokemon/7/"
        ),
        PokemonDTO(
            name = "wartortle",
            url = "https://pokeapi.co/api/v2/pokemon/8/"
        ),
        PokemonDTO(
            name = "blastoise",
            url = "https://pokeapi.co/api/v2/pokemon/9/"
        ),
        PokemonDTO(
            name = "caterpie",
            url = "https://pokeapi.co/api/v2/pokemon/10/"
        )
    )
)

val FakePokemonListEmptyResponse = SuccessListResponse<PokemonDTO>(
    data = emptyList()
)
