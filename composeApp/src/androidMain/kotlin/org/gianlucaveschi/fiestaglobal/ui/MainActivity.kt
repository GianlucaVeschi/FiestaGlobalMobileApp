package org.gianlucaveschi.fiestaglobal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import org.gianlucaveschi.fiestaglobal.ui.compose.ArtistsScreen


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel = ArtistsViewModel() // Initialize ViewModel outside of Composable scope

    setContent {
      // Collect the UI state from the ViewModel
      val uiState by viewModel.uiState.collectAsState()

      // Pass the UI model and retry action to the ArtistsScreen
      ArtistsScreen(
        uiModel = ArtistsUiState(
          artists = uiState.artists,
          isLoading = uiState.isLoading,
          error = uiState.error
        ),
        onRetry = { viewModel.loadArtists() }
      )
    }
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
    onRetry = {}
  )
}
