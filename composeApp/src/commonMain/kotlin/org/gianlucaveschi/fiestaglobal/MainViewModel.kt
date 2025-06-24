package org.gianlucaveschi.fiestaglobal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.domain.repository.EventRepository
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState


class MainViewModel(
  private val eventRepository: EventRepository
) : ViewModel() {
  private val viewModelScope = CoroutineScope(Dispatchers.Main)

  private val _uiState = MutableStateFlow(
    EventsUiState(
      daySchedules = emptyList(),
      isLoading = true
    )
  )
  val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

  init {
    loadEvents()
  }

  fun loadEvents() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, error = null) }
      try {
        val eventSchedule = eventRepository.getEvents()
        _uiState.update {
          it.copy(
            daySchedules = eventSchedule.schedule,
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
