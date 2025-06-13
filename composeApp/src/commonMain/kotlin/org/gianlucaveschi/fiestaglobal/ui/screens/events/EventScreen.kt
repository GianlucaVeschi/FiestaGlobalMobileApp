package org.gianlucaveschi.fiestaglobal.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.data.model.DaySchedule
import org.gianlucaveschi.fiestaglobal.data.model.EventItemResponse
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EventsScreen(
  uiModel: EventsUiState,
  onRetry: () -> Unit,
  onEventClick: (EventItemResponse) -> Unit = {}
) {
  val tabTitles = uiModel.daySchedules.map { it.day }
  val pagerState = rememberPagerState { tabTitles.size }
  val coroutineScope = rememberCoroutineScope()
  var searchQuery by remember { mutableStateOf("") }

  if (uiModel.daySchedules.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
      contentAlignment = Alignment.Center
    ) {
      if (uiModel.isLoading) {
        CircularProgressIndicator()
      } else if (uiModel.error != null) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("Error: ${uiModel.error}")
          Spacer(modifier = Modifier.height(16.dp))
          Button(onClick = onRetry) {
            Text("Retry")
          }
        }
      } else {
        Text("No schedule available")
      }
    }
    return
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    TabRow(
      selectedTabIndex = pagerState.currentPage,
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
      containerColor = Color.White,
      contentColor = Color.Black,
      indicator = { tabPositions ->
        if (tabPositions.isNotEmpty() && pagerState.currentPage < tabPositions.size) {
          TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
            color = Color.Black
          )
        }
      }
    ) {
      tabTitles.forEachIndexed { index, title ->
        Tab(
          selected = pagerState.currentPage == index,
          onClick = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(index)
            }
          },
          text = { Text(text = title) },
          selectedContentColor = Color.Black,
          unselectedContentColor = Color.Gray
        )
      }
    }

    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      label = { Text("Cerca artisti e performance") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Gray
      ),
      shape = RoundedCornerShape(8.dp),
      trailingIcon = {
        if (searchQuery.isNotBlank()) {
          IconButton(onClick = { searchQuery = "" }) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
          }
        } else {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
      }
    )

    HorizontalPager(
      state = pagerState,
      modifier = Modifier
        .weight(1f)
    ) { page ->
      if (page < uiModel.daySchedules.size) {
        EventContent(
          events = uiModel.daySchedules[page].events,
          onRetry = onRetry,
          onEventClick = onEventClick,
          searchQuery = searchQuery
        )
      }
    }
  }
}

@Composable
fun EventContent(
  events: List<EventItemResponse>,
  onRetry: () -> Unit,
  onEventClick: (EventItemResponse) -> Unit,
  searchQuery: String
) {
  val filteredEvents = remember(events, searchQuery) {
    if (searchQuery.isBlank()) {
      events
    } else {
      events.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
            it.location.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  val eventsByTime = filteredEvents.groupBy { it.time }

  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    if (filteredEvents.isEmpty()) {
      Text(
        text = "Nessun risultato trovato",
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        style = MaterialTheme.typography.bodyMedium
      )
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
      ) {
        eventsByTime.forEach { (time, eventsAtTime) ->
          item {
            TimeHeader(time = time)
          }
          items(eventsAtTime) { event ->
            EventItem(
              event = event,
              onClick = { onEventClick(event) }
            )
          }
        }
      }
    }
  }
}

@Composable
fun TimeHeader(time: String) {
  Text(
    text = time,
    style = MaterialTheme.typography.headlineLarge,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp, horizontal = 8.dp),
    color = Color.Black
  )
}

@Composable
fun EventItem(
  event: EventItemResponse,
  onClick: () -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(16.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column {
//      Image(
//        painter = painterResource(id = R.drawable.fiesta_global_placeholder),
//        contentDescription = null,
//        modifier = Modifier
//          .fillMaxWidth()
//          .height(150.dp),
//        contentScale = ContentScale.Crop
//      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = event.name,
            style = MaterialTheme.typography.titleLarge
          )
          Text(
            text = "Palco - ${event.location}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun AppAndroidPreview() {
  EventsScreen(
    uiModel = EventsUiState(
      daySchedules = listOf(
        DaySchedule(
          day = "Giovedì",
          events = listOf(
            EventItemResponse(
              name = "Laboratori artistici e creativi per bambini",
              time = "18:00",
              location = "Teatro delle isole"
            ),
            EventItemResponse(
              name = "Apertura Mostra Foto e Video",
              time = "18:00",
              location = "SOTTOMURA STAGE"
            ),
            EventItemResponse(
              name = "Giochi di una volta",
              time = "19:00",
              location = "Teatro delle isole"
            ),
            EventItemResponse(
              name = "Concerto Jazz",
              time = "19:00",
              location = "SOTTOMURA STAGE"
            ),
            EventItemResponse(
              name = "Spettacolo teatrale",
              time = "21:00",
              location = "Teatro delle isole"
            )
          )
        ),
        DaySchedule(
          day = "Venerdì",
          events = listOf(
            EventItemResponse(
              name = "Workshop di pittura",
              time = "17:00",
              location = "Teatro delle isole"
            ),
            EventItemResponse(
              name = "Concerto rock",
              time = "20:00",
              location = "SOTTOMURA STAGE"
            )
          )
        )
      ),
      isLoading = false,
      error = null
    ),
    onRetry = {},
    onEventClick = {}
  )
}
