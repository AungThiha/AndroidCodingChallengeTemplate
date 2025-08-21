package io.github.aungthiha.fullscreenerror

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.aungthiha.composable.SpacerL
import io.github.aungthiha.models.unpackString
import io.github.aungthiha.theme.AppTheme
import io.github.aungthiha.theme.AungThihaTheme

@Composable
fun FullScreenError(
    state: FullScreenErrorState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.padding(AppTheme.dimensions.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.icon?.let { icon ->
                Image(
                    imageVector = state.icon,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                SpacerL()
            }

            Text(
                text = state.title.unpackString(context),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            SpacerL()

            Text(
                text = state.body.unpackString(context),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            state.button?.let { button ->
                SpacerL()
                Button(
                    onClick = button.onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = button.text.unpackString(context))
                }
            }
        }
    }
}

@Preview
@Composable
fun GeneralErrorPreview() {
    AungThihaTheme {
        FullScreenError(state = createGeneralFullScreenErrorState {  })
    }
}

@Preview
@Composable
fun NetworkErrorPreview() {
    AungThihaTheme {
        FullScreenError(state = createNetworkFullScreenErrorState {  })
    }
}
