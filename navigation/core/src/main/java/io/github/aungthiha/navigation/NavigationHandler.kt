package io.github.aungthiha.navigation

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface NavigationHandler {
    fun onNavigateUp(): Deferred<Boolean>
    fun onNavigation(destination: Destination, navigationOptions: NavigationOptions): Job
}
