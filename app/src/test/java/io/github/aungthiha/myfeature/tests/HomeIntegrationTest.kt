package io.github.aungthiha.myfeature.tests

import io.github.aungthiha.coroutines.TestDispatcherExtension
import io.github.aungthiha.design.shouldEqualTo
import io.github.aungthiha.design.R as DesignR
import io.github.aungthiha.di.core.KoinTestExtension
import io.github.aungthiha.myfeature.presentation.home.HomeContract
import io.github.aungthiha.myfeature.presentation.home.HomeViewModel
import io.github.aungthiha.myfeature.testdoubles.StubMyfeatureDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(KoinTestExtension::class)
class HomeIntegrationTest : KoinTest {
    private val testScope = TestScope()

    @JvmField
    @RegisterExtension
    val testDispatcher = TestDispatcherExtension(testCoroutineScheduler = testScope.testScheduler)

    private val stubMyfeatureDataSource by inject<StubMyfeatureDataSource>()

    @Test
    fun `GIVEN successful API response, WHEN HomeViewModel is initialized, THEN state shows pokemon list`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListSuccess()

            // Act - ViewModel loads data in init block
            val viewModel = get<HomeViewModel>()

            // Assert
            val state = viewModel.state.first()
            Assertions.assertTrue(state is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN empty pokemon list response, WHEN HomeViewModel is initialized, THEN state shows EmptyPokemonList`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListSuccessEmpty()

            // Act - ViewModel loads data in init block
            val viewModel = get<HomeViewModel>()

            // Assert
            val state = viewModel.state.first()
            Assertions.assertTrue(state is HomeContract.State.EmptyPokemonList)
        }

    @Test
    fun `GIVEN network failure then success, WHEN retry is clicked, THEN state transitions from error to loading to content`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithIOException()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()

            // Assert - Initial error state
            val errorState = viewModel.state.first()
            Assertions.assertTrue(errorState is HomeContract.State.FullScreenError)
            val fullScreenErrorState = (errorState as HomeContract.State.FullScreenError).data

            // Verify network error content
            fullScreenErrorState.title shouldEqualTo DesignR.string.title_network_error
            fullScreenErrorState.body shouldEqualTo DesignR.string.check_your_internet_connection
            Assertions.assertNotNull(fullScreenErrorState.button)
            fullScreenErrorState.button!!.text shouldEqualTo DesignR.string.retry

            // Arrange - Stub success for retry
            stubMyfeatureDataSource.stubGetPokemonListSuccessWithDelay()

            // Act - Retry
            fullScreenErrorState.button!!.onClick()

            // Assert - Loading state
            val loadingState = viewModel.state.first()
            Assertions.assertTrue(loadingState is HomeContract.State.Loading)

            advanceUntilIdle()

            // Assert - Success state
            val successState = viewModel.state.first()
            Assertions.assertTrue(successState is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN general failure then success, WHEN retry is clicked, THEN state transitions from error to loading to content`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithGeneralException()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()
            advanceUntilIdle()

            // Assert - Initial error state
            val errorState = viewModel.state.first()
            Assertions.assertTrue(errorState is HomeContract.State.FullScreenError)
            val fullScreenErrorState = (errorState as HomeContract.State.FullScreenError).data

            // Verify general error content
            fullScreenErrorState.title shouldEqualTo DesignR.string.title_something_went_wrong
            fullScreenErrorState.body shouldEqualTo DesignR.string.something_went_wrong
            Assertions.assertNotNull(fullScreenErrorState.button)
            fullScreenErrorState.button!!.text shouldEqualTo DesignR.string.retry

            // Arrange - Stub success for retry
            stubMyfeatureDataSource.stubGetPokemonListSuccessWithDelay()

            // Act - Retry
            fullScreenErrorState.button!!.onClick()

            // Assert - Loading state
            val loadingState = viewModel.state.first()
            Assertions.assertTrue(loadingState is HomeContract.State.Loading)

            advanceUntilIdle()

            // Assert - Success state
            val successState = viewModel.state.first()
            Assertions.assertTrue(successState is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN network failure then network failure then success, WHEN retry is clicked twice, THEN eventually succeeds`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithIOException()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()

            // Assert - Initial error state
            val firstErrorState = viewModel.state.first()
            Assertions.assertTrue(firstErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub another failure for first retry
            stubMyfeatureDataSource.stubGetPokemonListFailureWithIOException()

            // Act - First retry fails
            val firstFullScreenErrorState =
                (firstErrorState as HomeContract.State.FullScreenError).data
            firstFullScreenErrorState.button!!.onClick()

            // Assert - Still in error state
            val secondErrorState = viewModel.state.first()
            Assertions.assertTrue(secondErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub success for second retry
            stubMyfeatureDataSource.stubGetPokemonListSuccess()

            // Act - Second retry succeeds
            val secondFullScreenErrorState =
                (secondErrorState as HomeContract.State.FullScreenError).data
            secondFullScreenErrorState.button!!.onClick()

            // Assert - Success state
            val successState = viewModel.state.first()
            Assertions.assertTrue(successState is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN general failure then network failure then success, WHEN retry is clicked twice, THEN eventually succeeds`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithGeneralException()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()

            // Assert - Initial error state
            val firstErrorState = viewModel.state.first()
            Assertions.assertTrue(firstErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub network failure for first retry
            stubMyfeatureDataSource.stubGetPokemonListFailureWithIOException()

            // Act - First retry fails with network error
            val firstFullScreenErrorState =
                (firstErrorState as HomeContract.State.FullScreenError).data
            firstFullScreenErrorState.button!!.onClick()

            // Assert - Still in error state
            val secondErrorState = viewModel.state.first()
            Assertions.assertTrue(secondErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub success for second retry
            stubMyfeatureDataSource.stubGetPokemonListSuccess()

            // Act - Second retry succeeds
            val secondFullScreenErrorState =
                (secondErrorState as HomeContract.State.FullScreenError).data
            secondFullScreenErrorState.button!!.onClick()

            // Assert - Success state
            val successState = viewModel.state.first()
            Assertions.assertTrue(successState is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN HTTP error then general failure then success, WHEN retry is clicked twice, THEN eventually succeeds`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithHttpExceptionUnknownError()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()

            // Assert - Initial error state
            val firstErrorState = viewModel.state.first()
            Assertions.assertTrue(firstErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub general failure for first retry
            stubMyfeatureDataSource.stubGetPokemonListFailureWithGeneralException()

            // Act - First retry fails with general error
            val firstFullScreenErrorState =
                (firstErrorState as HomeContract.State.FullScreenError).data
            firstFullScreenErrorState.button!!.onClick()

            // Assert - Still in error state
            val secondErrorState = viewModel.state.first()
            Assertions.assertTrue(secondErrorState is HomeContract.State.FullScreenError)

            // Arrange - Stub success for second retry
            stubMyfeatureDataSource.stubGetPokemonListSuccess()

            // Act - Second retry succeeds
            val secondFullScreenErrorState =
                (secondErrorState as HomeContract.State.FullScreenError).data
            secondFullScreenErrorState.button!!.onClick()

            // Assert - Success state
            val successState = viewModel.state.first()
            Assertions.assertTrue(successState is HomeContract.State.Content)
        }

    @Test
    fun `GIVEN HTTP unauthorized error, WHEN HomeViewModel is initialized, THEN state shows general error`() =
        runTest {
            // Arrange
            stubMyfeatureDataSource.stubGetPokemonListFailureWithHttpExceptionUnauthorized()

            // Act - Initial load fails
            val viewModel = get<HomeViewModel>()

            // Assert - Error state with general error message
            val errorState = viewModel.state.first()
            Assertions.assertTrue(errorState is HomeContract.State.FullScreenError)
            val fullScreenErrorState = (errorState as HomeContract.State.FullScreenError).data

            // Verify general error content (unauthorized gets mapped to general error)
            fullScreenErrorState.title shouldEqualTo DesignR.string.title_something_went_wrong
            fullScreenErrorState.body shouldEqualTo DesignR.string.something_went_wrong
        }
}
