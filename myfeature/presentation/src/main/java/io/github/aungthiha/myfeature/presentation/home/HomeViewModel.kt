package io.github.aungthiha.myfeature.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aungthiha.fullscreenerror.createGeneralFullScreenErrorState
import io.github.aungthiha.fullscreenerror.createNetworkFullScreenErrorState
import io.github.aungthiha.myfeature.domain.models.Pokemon
import io.github.aungthiha.operation.FailureType
import io.github.aungthiha.operation.Outcome
import io.github.aungthiha.operation.SuspendOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import io.github.aungthiha.operation.invoke

class HomeViewModel(
    private val getPokemons: SuspendOperation<Unit, List<Pokemon>>,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeContract.State>(HomeContract.State.Loading)
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _state.value = HomeContract.State.Loading

            val result = getPokemons()
            when (result) {
                is Outcome.Failure<List<Pokemon>> -> result.handleInitialLoadFailure()
                is Outcome.Success<List<Pokemon>> -> result.handleInitialLoadSuccess()
            }
        }
    }

    private fun Outcome.Success<List<Pokemon>>.handleInitialLoadSuccess() {
        if (data.isEmpty()) {
            _state.value = HomeContract.State.EmptyPokemonList
        } else {
            _state.value = HomeContract.State.Content(data)
        }
    }

    private fun Outcome.Failure<List<Pokemon>>.handleInitialLoadFailure() {
        val fullScreenErrorState = when (type) {
            FailureType.NETWORK -> {
                createNetworkFullScreenErrorState(
                    onButtonClick = ::loadPokemons
                )
            }

            FailureType.GENERAL -> {
                createGeneralFullScreenErrorState(
                    onButtonClick = ::loadPokemons
                )
            }
        }
        _state.value = HomeContract.State.FullScreenError(fullScreenErrorState)
    }
}
