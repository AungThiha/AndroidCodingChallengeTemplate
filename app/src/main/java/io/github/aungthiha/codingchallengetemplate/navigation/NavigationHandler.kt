package io.github.aungthiha.codingchallengetemplate.navigation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import io.github.aungthiha.coroutines.AppDispatchers
import io.github.aungthiha.navigation.Destination
import io.github.aungthiha.navigation.NavigationHandler
import io.github.aungthiha.navigation.NavigationOptions
import io.github.aungthiha.navigation.applyNavigationOptions
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Naming a function with a capital letter is a convention in Kotlin to indicate that it's a factory function.
 * Take CoroutineScope function for example. It looks like a class but it's actually a function. (Of course, there's also CoroutineScope interface)
 * There's also MutableSharedFlow function with a capital letter. (Of course, there's also MutableSharedFlow interface)
 * This pattern is discouraged only in Java, not in Kotlin. In Kotlin, it's a common practice.
 * */
fun NavigationHandler(
    lifecycleScope: LifecycleCoroutineScope,
    navHostController: NavHostController
) = object : NavigationHandler {
    /**
     * [onNavigateUp] can be called from any thread but
     * [androidx.navigation.NavController.navigateUp] needs to be called from main thread
     * That's why it uses [lifecycleScope] to ensure the function is main-safe
     * This separates the concern from the caller,
     * meaning the caller can use it without worrying about main-safety
     * */
    override fun onNavigateUp(): Deferred<Boolean> = lifecycleScope.async(AppDispatchers.main) {
        navHostController.navigateUp()
    }

    /**
     * [onNavigation] can be called from any thread but
     * [androidx.navigation.NavController.navigate] needs to be called from main thread
     * That's why it uses [lifecycleScope] to ensure the function is main-safe
     * This separates the concern from the caller,
     * meaning the caller can use it without worrying about main-safety
     * */
    override fun onNavigation(
        destination: Destination,
        navigationOptions: NavigationOptions
    ) = lifecycleScope.launch(AppDispatchers.main) {
        navHostController.navigate(destination) {
            applyNavigationOptions(navigationOptions)
        }
    }
}
