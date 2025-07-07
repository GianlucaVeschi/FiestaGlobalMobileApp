package org.gianlucaveschi.fiestaglobal.ui.screens.events

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import coil3.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.domain.model.DaySchedule
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EventsScreen(
  uiState: EventsUiState,
  onRetry: () -> Unit,
  onEventClick: (Event) -> Unit = {},
  lazyListState: LazyListState = rememberLazyListState(),
  pagerState: PagerState? = null
) {
  when (uiState) {
    is EventsUiState.Loading -> {
      LoadingEventScreen()
    }

    is EventsUiState.Error -> {
      ErrorEventScreen(
        message = uiState.message,
        onRetry = onRetry
      )
    }

    is EventsUiState.Success -> {
      SuccessEventScreen(
        daySchedules = uiState.daySchedules,
        onRetry = onRetry,
        onEventClick = onEventClick,
        lazyListState = lazyListState,
        pagerState = pagerState
      )
    }
  }
}

@Composable
private fun LoadingEventScreen() {
  Column(
    modifier = Modifier.fillMaxSize()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
      repeat(3) { index ->
        Box(
          modifier = Modifier
            .width(80.dp)
            .height(32.dp)
            .shimmerEffect()
        )
        if (index < 2) {
          Spacer(modifier = Modifier.width(16.dp))
        }
      }
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(16.dp)
        .shimmerEffect()
    )

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
      items(6) {
        ShimmerEventCard()
      }
    }
  }
}

@Composable
private fun ErrorEventScreen(
  message: String,
  onRetry: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .systemBarsPadding(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Ci dispiace ma si è verificato un errore, controlla la tua connessione a Internet",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.error
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error
      )
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = onRetry,
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.error,
          contentColor = MaterialTheme.colorScheme.onError,
        )
      ) {
        Text("Riprova")
      }
    }
  }
}

@Composable
private fun SuccessEventScreen(
  daySchedules: List<DaySchedule>,
  onRetry: () -> Unit,
  onEventClick: (Event) -> Unit,
  lazyListState: LazyListState,
  pagerState: PagerState?
) {
  val tabTitles = daySchedules.map { it.day }
  val actualPagerState = pagerState ?: rememberPagerState { tabTitles.size }
  val coroutineScope = rememberCoroutineScope()
  var searchQuery by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current

  if (daySchedules.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
      contentAlignment = Alignment.Center
    ) {
      Text("No schedule available")
    }
    return
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    TabRow(
      selectedTabIndex = actualPagerState.currentPage,
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
      containerColor = Color.White,
      contentColor = Color.Black,
      indicator = { tabPositions ->
        if (tabPositions.isNotEmpty() && actualPagerState.currentPage < tabPositions.size) {
          TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[actualPagerState.currentPage]),
            color = Color.Black
          )
        }
      }
    ) {
      tabTitles.forEachIndexed { index, title ->
        Tab(
          selected = actualPagerState.currentPage == index,
          onClick = {
            coroutineScope.launch {
              actualPagerState.animateScrollToPage(index)
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
        .padding(16.dp)
        .focusRequester(focusRequester),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Gray
      ),
      shape = RoundedCornerShape(8.dp),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(
        onSearch = {
          focusManager.clearFocus()
        },
        onDone = {
          focusManager.clearFocus()
        }
      ),
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
      state = actualPagerState,
      modifier = Modifier
        .weight(1f)
    ) { page ->
      if (page < daySchedules.size) {
        EventContent(
          events = daySchedules[page].events,
          onRetry = onRetry,
          onEventClick = onEventClick,
          searchQuery = searchQuery,
          lazyListState = lazyListState
        )
      }
    }
  }
}

@Composable
private fun ShimmerEventCard() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    shape = RoundedCornerShape(16.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          // Shimmer for event name
          Box(
            modifier = Modifier
              .fillMaxWidth(0.7f)
              .height(20.dp)
              .shimmerEffect()
          )
          Spacer(modifier = Modifier.height(8.dp))
          // Shimmer for location
          Box(
            modifier = Modifier
              .fillMaxWidth(0.5f)
              .height(16.dp)
              .shimmerEffect()
          )
        }
      }
    }
  }
}

@Composable
private fun Modifier.shimmerEffect(): Modifier = composed {
  val transition = rememberInfiniteTransition()
  val alpha = transition.animateFloat(
    initialValue = 0.2f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1000),
      repeatMode = RepeatMode.Reverse
    )
  ).value

  background(
    brush = Brush.linearGradient(
      colors = listOf(
        Color.LightGray.copy(alpha = alpha),
        Color.Gray.copy(alpha = alpha)
      ),
      start = Offset.Zero,
      end = Offset(1000f, 1000f)
    ),
    shape = RoundedCornerShape(4.dp)
  )
}

@Composable
fun EventContent(
  events: List<Event>,
  onRetry: () -> Unit,
  onEventClick: (Event) -> Unit,
  searchQuery: String,
  lazyListState: LazyListState
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
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        state = lazyListState
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
  event: Event,
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
      // Only display the image view if the URL is not blank
      if (event.imageUrl.isNotBlank()) {
        SubcomposeAsyncImage(
          model = event.imageUrl,
          contentDescription = event.name,
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
          contentScale = ContentScale.Crop,
          loading = {
            // Show a spinner in the center while the image is loading
            Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
            ) {
              CircularProgressIndicator()
            }
          }
          // The success state is handled by default, which displays the image.
          // An optional error placeholder can be provided via the `error` parameter.
        )
      }
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
fun LoadingEventScreenPreview() {
  EventsScreen(
    uiState = EventsUiState.Loading,
    onRetry = {},
    onEventClick = {}
  )
}

@Preview
@Composable
fun ErrorEventScreenPreview() {
  EventsScreen(
    uiState = EventsUiState.Error("Network connection failed"),
    onRetry = {},
    onEventClick = {}
  )
}

@Preview
@Composable
fun AppAndroidPreview() {
  EventsScreen(
    uiState = EventsUiState.Success(
      daySchedules = listOf(
        DaySchedule(
          day = "Giovedì",
          events = listOf(
            Event(
              name = "Laboratori artistici e creativi per bambini",
              time = "18:00",
              location = "Teatro delle isole",
              imageUrl = "",
            ),
            Event(
              name = "Apertura Mostra Foto e Video",
              time = "18:00",
              location = "SOTTOMURA STAGE",
              imageUrl = "",
            ),
            Event(
              name = "Giochi di una volta",
              time = "19:00",
              location = "Teatro delle isole",
              imageUrl = "",
            ),
            Event(
              name = "Concerto Jazz",
              time = "19:00",
              location = "SOTTOMURA STAGE",
              imageUrl = "",
            ),
            Event(
              name = "Spettacolo teatrale",
              time = "21:00",
              location = "Teatro delle isole",
              imageUrl = "",
            )
          )
        ),
        DaySchedule(
          day = "Venerdì",
          events = listOf(
            Event(
              name = "Workshop di pittura",
              time = "17:00",
              location = "Teatro delle isole",
              imageUrl = "",
            ),
            Event(
              name = "Concerto rock",
              time = "20:00",
              location = "SOTTOMURA STAGE",
              imageUrl = "",
            )
          )
        )
      )
    ),
    onRetry = {},
    onEventClick = {}
  )
}
