package io.github.aungthiha.navigation

import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.component.get
import org.koin.test.KoinTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun KoinTest.runNavTest(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = 60.seconds,
    testBody: suspend TestScope.(SpyNavigationHandler) -> Unit
): TestResult {
    val testScope = TestScope(context)
    val spyNavigationHandler = SpyNavigationHandler(testScope)
    get<NavigationDispatcher>().setHandler(spyNavigationHandler)
    return testScope.runTest(timeout) {
        testBody(spyNavigationHandler)
    }
}

fun KoinTest.runNavTest(
    testScope: TestScope,
    timeout: Duration = 60.seconds,
    testBody: suspend TestScope.(SpyNavigationHandler) -> Unit
): TestResult {
    val spyNavigationHandler = SpyNavigationHandler(testScope)
    get<NavigationDispatcher>().setHandler(spyNavigationHandler)
    return testScope.runTest(timeout) {
        testBody(spyNavigationHandler)
    }
}
