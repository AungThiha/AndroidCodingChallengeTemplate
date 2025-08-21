package io.github.aungthiha.codingchallengetemplate

import android.R.id.home
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.aungthiha.codingchallengetemplate.navigation.NavigationHandler
import io.github.aungthiha.myfeature.presentation.navigation.HomeRoute
import io.github.aungthiha.myfeature.presentation.navigation.home
import io.github.aungthiha.navigation.Destination
import io.github.aungthiha.navigation.NavigationDispatcher
import io.github.aungthiha.theme.AungThihaTheme
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AungThihaTheme {
                val navController: NavHostController = rememberNavController()
                koinInject<NavigationDispatcher>().setHandler(
                    NavigationHandler(
                        LocalLifecycleOwner.current.lifecycleScope,
                        navController
                    )
                )

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    home()
                }
            }
        }
    }
}
