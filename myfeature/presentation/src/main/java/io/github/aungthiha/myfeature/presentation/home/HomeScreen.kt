package io.github.aungthiha.myfeature.presentation.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeContainer() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state.value) {

        is HomeContract.State.Content -> {
            // TODO display list
        }

        is HomeContract.State.FullScreenError -> HomeContract.State.FullScreenError(currentState.data)

        HomeContract.State.Loading -> {
            // TODO add shimmer effect
        }

        HomeContract.State.EmptyPokemonList -> {
            // show empty message
        }
    }
}
