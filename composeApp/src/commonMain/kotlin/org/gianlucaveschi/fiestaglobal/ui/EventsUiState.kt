package org.gianlucaveschi.fiestaglobal.ui

import org.gianlucaveschi.fiestaglobal.domain.model.DaySchedule

data class EventsUiState(
  val daySchedules: List<DaySchedule>,
  val isLoading: Boolean = false,
  val error: String? = null
)