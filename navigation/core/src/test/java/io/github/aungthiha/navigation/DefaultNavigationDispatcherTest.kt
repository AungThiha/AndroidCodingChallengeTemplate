package io.github.aungthiha.navigation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Job
import org.junit.jupiter.api.Test

class DefaultNavigationDispatcherTest {

    private val navigationDispatcher = DefaultNavigationDispatcher()
    private val handler = mockk<NavigationHandler>()

    @Test
    fun `WHEN navigate to LoginRoute with launchSingleTop = true, THEN onNavigation called with correct options`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(LoginRoute, launchSingleTop = true)

        verify {
            handler.onNavigation(
                LoginRoute,
                NavigationOptions(
                    launchSingleTop = true
                )
            )
        }
    }

    @Test
    fun `WHEN navigate to LoginRoute with PopUpTo = SplashRoute, THEN onNavigation called with PopUpTo route`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(LoginRoute, popUpTo = SplashRoute)

        verify {
            handler.onNavigation(
                LoginRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.PopUpTo(SplashRoute)
                )
            )
        }
    }

    @Test
    fun `WHEN navigate to LoginRoute with PopUpTo SplashRoute and isInclusive = true, THEN onNavigation called with PopUpTo and inclusive flag`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(LoginRoute, popUpTo = SplashRoute, isInclusive = true)

        verify {
            handler.onNavigation(
                LoginRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.PopUpTo(SplashRoute, true)
                )
            )
        }
    }

    @Test
    fun `WHEN navigate to LoginRoute with clearBackStack = true, THEN onNavigation called with Clear option`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(LoginRoute, clearBackStack = true)

        verify {
            handler.onNavigation(
                LoginRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.Clear
                )
            )
        }
    }
}

data object LoginRoute : Destination
data object SplashRoute : Destination
