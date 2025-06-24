package org.gianlucaveschi.fiestaglobal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.domain.model.Result
import org.gianlucaveschi.fiestaglobal.domain.repository.EventRepository
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState


class MainViewModel(
  private val eventRepository: EventRepository
) : ViewModel() {

  private val viewModelScope = CoroutineScope(Dispatchers.Main)

  private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
  val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

  init {
    loadEvents()
  }

  fun loadEvents() {
    viewModelScope.launch {
      eventRepository.getEvents().collect { result ->
        _uiState.value = when (result) {
          is Result.Loading -> EventsUiState.Loading
          is Result.Success -> EventsUiState.Success(result.data.schedule)
          is Result.Error -> EventsUiState.Error(
            result.exception.message ?: "An unknown error occurred"
          )
        }
      }
    }
  }
}
