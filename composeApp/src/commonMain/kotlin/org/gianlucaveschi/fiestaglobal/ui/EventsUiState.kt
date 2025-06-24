package org.gianlucaveschi.fiestaglobal.ui

import org.gianlucaveschi.fiestaglobal.domain.model.DaySchedule

sealed class EventsUiState {
  data object Loading : EventsUiState()
  data class Error(val message: String) : EventsUiState()
  data class Success(val daySchedules: List<DaySchedule>) : EventsUiState()
}