package org.gianlucaveschi.fiestaglobal.ui.artists

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.data.fetchArtists
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState


class ArtistsViewModel {
  private val viewModelScope = CoroutineScope(Dispatchers.Main)

  private val _uiState = MutableStateFlow(ArtistsUiState(isLoading = true))
  val uiState: StateFlow<ArtistsUiState> = _uiState.asStateFlow()

  init {
    loadArtists()
  }

  fun loadArtists() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, error = null) }
      try {
        val artistsList = fetchArtists()
        _uiState.update {
          it.copy(
            artists = artistsList.artists,
            isLoading = false
          )
        }
      } catch (e: Exception) {
        _uiState.update {
          it.copy(
            isLoading = false,
            error = e.message ?: "An unknown error occurred"
          )
        }
      }
    }
  }
}
