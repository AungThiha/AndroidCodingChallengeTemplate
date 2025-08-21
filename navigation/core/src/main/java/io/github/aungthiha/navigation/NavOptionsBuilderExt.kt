package io.github.aungthiha.navigation

import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.applyNavigationOptions(navigationOptions: NavigationOptions) {
    launchSingleTop = navigationOptions.launchSingleTop

    navigationOptions.backStackOptions?.let {
        when (it) {
            BackStackOptions.Clear -> {
                popUpTo(0)
            }

            is BackStackOptions.PopUpTo -> {
                popUpTo(it.popUpToDestination) {
                    inclusive = it.inclusive
                }
            }
        }
    }
}
