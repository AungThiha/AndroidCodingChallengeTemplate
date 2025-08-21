package io.github.aungthiha.myfeature.data

import io.github.aungthiha.myfeature.data.remote.MyfeatureDataSource
import io.github.aungthiha.myfeature.data.remote.dtos.toPokemon
import io.github.aungthiha.myfeature.domain.MyfeatureRepository
import io.github.aungthiha.myfeature.domain.models.Pokemon
import io.github.aungthiha.operation.suspendNetworkOperation

class MyfeatureRepositoryImpl(
    private val myfeatureDataSource: MyfeatureDataSource
) : MyfeatureRepository {
    override val getPokemons = suspendNetworkOperation<Unit, List<Pokemon>> {
        myfeatureDataSource.getPokemonList().data.map { it.toPokemon() }
    }
}
