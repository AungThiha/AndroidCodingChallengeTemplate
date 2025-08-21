package io.github.aungthiha.navigation

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SpyNavigationHandler(
    private val coroutineScope: TestScope
) : NavigationHandler {

    @OptIn(ExperimentalCoroutinesApi::class)
    val onNavigationTestDispatcher = UnconfinedTestDispatcher()

    val navigateCalls = mutableListOf<Pair<Destination, NavigationOptions>>()
    var navigateUpCallCount = 0

    val navigateJobs = mutableListOf<Job>()

    override fun onNavigateUp(): CompletableDeferred<Boolean> {
        navigateUpCallCount++
        return CompletableDeferred(true)
    }

    override fun onNavigation(destination: Destination, navigationOptions: NavigationOptions): Job {
        navigateCalls += destination to navigationOptions
        return coroutineScope.launch(onNavigationTestDispatcher) {

        }.also {
            navigateJobs += it
        }
    }
}

infix fun SpyNavigationHandler.shouldNavigateTo(destination: Destination) {
    assertTrue(
        navigateCalls.any { (d, _) -> d == destination },
        "Expected navigation to $destination but got: ${navigateCalls.map { it.first }}"
    )
}

infix fun SpyNavigationHandler.shouldNavigateTo(expected: DestinationWithOptions) {
    val match = navigateCalls.any { (actualDest, actualOpts) ->
        actualDest == expected.destination &&
                actualOpts.launchSingleTop == expected.launchSingleTop &&
                actualOpts.backStackOptions == expected.backStackOptions
    }

    assert(match) {
        "Expected navigation to ${expected.destination} with $expected, but got:\n" +
                navigateCalls.joinToString("\n") { (dest, opt) -> "$dest with $opt" }
    }
}

fun SpyNavigationHandler.shouldNavigateUp(times: Int = 1) {
    assertEquals(times, navigateUpCallCount, "Expected navigateUp() to be called $times times")
}

fun SpyNavigationHandler.shouldNotNavigate() {
    assert(navigateCalls.isEmpty()) {
        "Expected no navigation but got: $navigateCalls"
    }
}
