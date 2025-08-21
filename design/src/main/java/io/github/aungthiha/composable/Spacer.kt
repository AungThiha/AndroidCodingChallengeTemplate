package io.github.aungthiha.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import io.github.aungthiha.theme.AppTheme

@Composable
@NonRestartableComposable
fun Spacer2Xs(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.xxs))
}

@Composable
@NonRestartableComposable
fun SpacerXs(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.xs))
}

@Composable
@NonRestartableComposable
fun SpacerS(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.s))
}

@Composable
@NonRestartableComposable
fun SpacerM(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.m))
}

@Composable
@NonRestartableComposable
fun SpacerL(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.l))
}

@Composable
@NonRestartableComposable
fun SpacerXl(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.xl))
}

@Composable
@NonRestartableComposable
fun Spacer2Xl(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.xxl))
}

@Composable
@NonRestartableComposable
fun Spacer3Xl(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(AppTheme.dimensions.xxxl))
}
