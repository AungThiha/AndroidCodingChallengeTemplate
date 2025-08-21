package io.github.aungthiha.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.aungthiha.theme.BlackAlpha50

@Composable
fun LoadingOverlay() {
    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BlackAlpha50)
                .clickable(enabled = false) {} // Disables clicks on the overlay
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
    }
}
