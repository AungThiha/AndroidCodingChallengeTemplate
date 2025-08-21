package io.github.aungthiha.myfeature.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valentinilk.shimmer.shimmer
import io.github.aungthiha.composable.SpacerS
import io.github.aungthiha.theme.AppTheme
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
            HomeShimmer()
        }

        HomeContract.State.EmptyPokemonList -> {
            // show empty message
        }
    }
}

// TODO edit to make it match the content
@Composable
fun HomeShimmer() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    AppTheme.dimensions.l
                )
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .shimmer()
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
            )
            SpacerS()
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
            )
            SpacerS()
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
            )
        }
    }
}
