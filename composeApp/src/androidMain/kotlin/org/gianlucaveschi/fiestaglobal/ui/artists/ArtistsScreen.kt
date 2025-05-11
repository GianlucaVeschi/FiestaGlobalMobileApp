package org.gianlucaveschi.fiestaglobal.ui.artists

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState

@Composable
fun ArtistsScreen(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit,
) {
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  val tabTitles = listOf("Gio 17", "Veb 18", "Sab 19", "Dom 20")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding()
  ) {
    TabRow(
      selectedTabIndex = selectedTabIndex,
      modifier = Modifier.fillMaxWidth()
    ) {
      tabTitles.forEachIndexed { index, title ->
        Tab(
          selected = selectedTabIndex == index,
          onClick = { selectedTabIndex = index },
          text = { Text(text = title) }
        )
      }
    }

    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .pointerInput(Unit) {
          detectHorizontalDragGestures { _, dragAmount ->
            when {
              dragAmount < -15 -> { // Swiping from right to left (next tab)
                if (selectedTabIndex < tabTitles.size - 1) {
                  selectedTabIndex++
                }
              }

              dragAmount > 15 -> { // Swiping from left to right (previous tab)
                if (selectedTabIndex > 0) {
                  selectedTabIndex--
                }
              }
            }
          }
        }
    ) {
      when (selectedTabIndex) {
        0 -> ThursdayArtists(uiModel, onRetry)
        1 -> FridayArtists()
        2 -> SaturdayArtists()
        3 -> SundayArtists()
      }
    }
  }
}

@Composable
fun ThursdayArtists(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit,
) {
  Box(
    modifier = Modifier
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
fun FridayArtists() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Artisti della venerdì",
      style = MaterialTheme.typography.h4
    )
  }
}

@Composable
fun SaturdayArtists() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Artisti del sabato",
      style = MaterialTheme.typography.h4
    )
  }
}

@Composable
fun SundayArtists() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Artisti della domenica",
      style = MaterialTheme.typography.h4
    )
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

@Preview
@Composable
fun AppAndroidPreview() {
  ArtistsScreen(
    uiModel = ArtistsUiState(
      artists = listOf(
        ArtistItemResponse(
          name = "Laboratori artistici e creativi per bambini a cura di TEATRO DELLE ISOLE",
          time = "18:00"
        ),
        ArtistItemResponse(
          name = "Apertura Mostra Foto e Video “Fiesta Global - 20 anni!”",
          time = "18:00"
        ),
        ArtistItemResponse(
          name = "Giochi di una volta e Laboratori a tema con la Capretta Cleopatra a cura di IL GIARDINO DEI COLORI",
          time = "19:00"
        ),
        ArtistItemResponse(
          name = "RAFFAELE DI PLACIDO presenta “L’uomo che uccise Mussolini” (Piemme, 2024)",
          time = "19:00"
        ),
      ),
      isLoading = false,
      error = null
    ),
    onRetry = {},
  )
}