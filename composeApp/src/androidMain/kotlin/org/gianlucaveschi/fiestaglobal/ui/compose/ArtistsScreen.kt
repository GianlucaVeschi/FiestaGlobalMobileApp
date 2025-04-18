package org.gianlucaveschi.fiestaglobal.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState

@Composable
fun ArtistsScreen(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit // Pass a lambda for retry actions
) {
  Box(modifier = Modifier
    .fillMaxSize()
    .systemBarsPadding()
  ) {
    when {
      uiModel.isLoading -> {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      }

      uiModel.error != null -> {
        Column(
          modifier = Modifier.align(Alignment.Center),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("Error: ${uiModel.error}")
          Spacer(modifier = Modifier.height(16.dp))
          Button(onClick = onRetry) {
            Text("Retry")
          }
        }
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp)
        ) {
          items(uiModel.artists) { artist ->
            ArtistItem(artist)
            Divider()
          }
        }
      }
    }
  }
}

@Composable
fun ArtistItem(artist: ArtistItemResponse) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
  ) {
    Text(
      text = artist.name,
      style = MaterialTheme.typography.h6
    )
    Text(
      text = "Time: ${artist.time}",
      style = MaterialTheme.typography.body2
    )
  }
}

