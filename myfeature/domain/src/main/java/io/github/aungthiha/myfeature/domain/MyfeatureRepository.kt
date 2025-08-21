package io.github.aungthiha.myfeature.domain

import io.github.aungthiha.myfeature.domain.models.Pokemon
import io.github.aungthiha.operation.SuspendOperation

interface MyfeatureRepository {
    val getPokemons: SuspendOperation<Unit, List<Pokemon>>
}
