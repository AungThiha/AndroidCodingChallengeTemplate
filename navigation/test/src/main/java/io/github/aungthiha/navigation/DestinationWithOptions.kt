package io.github.aungthiha.navigation

import io.github.aungthiha.navigation.BackStackOptions
import io.github.aungthiha.navigation.Destination

data class DestinationWithOptions(
    val destination: Destination,
    val launchSingleTop: Boolean = false,
    val backStackOptions: BackStackOptions? = null,
)
val Destination.withLaunchSingleTop: DestinationWithOptions
    get() = DestinationWithOptions(
        destination = this,
        launchSingleTop = true
    )
val Destination.withClearBackStack: DestinationWithOptions
    get() = DestinationWithOptions(
        destination = this,
        backStackOptions = BackStackOptions.Clear
    )
val Destination.withoutBackStackOptions: DestinationWithOptions
    get() = DestinationWithOptions(
        destination = this,
    )
fun Destination.withPopUpTo(popUpTo: Destination, isInclusive: Boolean = false): DestinationWithOptions =
    DestinationWithOptions(
        destination = this,
        backStackOptions = BackStackOptions.PopUpTo(popUpTo, isInclusive)
    )
