package io.github.aungthiha.myfeature.presentation.home

import io.github.aungthiha.fullscreenerror.FullScreenErrorState
import io.github.aungthiha.myfeature.domain.models.Pokemon

interface HomeContract {
    sealed class State {
        data class FullScreenError(val data: FullScreenErrorState) : State()
        data class Content(val pokemons: List<Pokemon>) : State()
        object EmptyPokemonList : State()
        object Loading : State()
    }

    /*
    add listener if you need
    interface Listener {
        fun onSomething()
        fun onButtonClick()
    }
    */
}
