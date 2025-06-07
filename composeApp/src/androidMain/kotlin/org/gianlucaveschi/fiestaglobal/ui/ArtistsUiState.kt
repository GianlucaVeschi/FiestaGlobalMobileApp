package org.gianlucaveschi.fiestaglobal.ui

import org.gianlucaveschi.fiestaglobal.data.model.DaySchedule

data class ArtistsUiState(
  val daySchedules: List<DaySchedule>,
  val isLoading: Boolean = false,
  val error: String? = null
)