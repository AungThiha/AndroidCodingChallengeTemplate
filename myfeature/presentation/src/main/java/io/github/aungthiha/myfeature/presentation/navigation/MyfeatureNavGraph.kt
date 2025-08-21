package io.github.aungthiha.myfeature.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.aungthiha.myfeature.presentation.home.HomeContainer
import io.github.aungthiha.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : Destination

fun NavGraphBuilder.home() {
    composable<HomeRoute> {
        HomeContainer()
    }
}
