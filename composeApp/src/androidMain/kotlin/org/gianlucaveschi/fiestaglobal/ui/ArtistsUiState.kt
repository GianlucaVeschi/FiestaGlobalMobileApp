package org.gianlucaveschi.fiestaglobal.ui

import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse

data class ArtistsUiState(
  val artists: List<ArtistItemResponse> = emptyList(),
  val isLoading: Boolean = false,
  val error: String? = null
)