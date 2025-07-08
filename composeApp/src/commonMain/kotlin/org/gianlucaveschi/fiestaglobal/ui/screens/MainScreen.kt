package org.gianlucaveschi.fiestaglobal.ui.screens

import EventDetailScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fiestaglobalmobileapp.composeapp.generated.resources.Res
import fiestaglobalmobileapp.composeapp.generated.resources.fiesta_global_date_banner
import org.gianlucaveschi.fiestaglobal.MainViewModel
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventsScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.artists.ArtistsScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.artists.hardcodedArtists
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
  var currentScreen by remember { mutableStateOf<ScreenState>(ScreenState.Info) }
  val mainViewModel: MainViewModel = koinInject()
  val uiState by mainViewModel.uiState.collectAsState()

  var selectedEvents by remember { mutableStateOf<Event?>(null) }
  val lazyListState = rememberLazyListState()

  val pagerState = when (val currentUiState = uiState) {
    is EventsUiState.Success -> rememberPagerState { currentUiState.daySchedules.size }
    else -> null
  }

  when (currentScreen) {
    is ScreenState.Info -> {
      var profileScreen by remember { mutableStateOf<ProfileScreenState>(ProfileScreenState.Main) }
      
      when (profileScreen) {
        is ProfileScreenState.Main -> MainProfileScreen(
          onArtistsClick = { profileScreen = ProfileScreenState.ArtistsDetail },
          onEventsClick = { currentScreen = ScreenState.Events }
        )

        is ProfileScreenState.ArtistsDetail -> ArtistsScreen(
          title = "Artisti & Band",
          onBackClick = { profileScreen = ProfileScreenState.Main }
        )
      }
    }

    is ScreenState.Events -> {
      if (selectedEvents != null) {
        EventDetailScreen(
          event = selectedEvents!!,
          onBackClick = { selectedEvents = null }
        )
      } else {
        EventsScreen(
          uiState = uiState,
          onRetry = { mainViewModel.loadEvents() },
          onEventClick = { event ->
            selectedEvents = event
          },
          lazyListState = lazyListState,
          pagerState = pagerState,
          onBackClick = { currentScreen = ScreenState.Info }
        )
      }
    }
  }
}

@Composable
fun MainProfileScreen(
  onArtistsClick: () -> Unit,
  onEventsClick: () -> Unit = {}
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 251, 229))
      .verticalScroll(scrollState)
  ) {
    Image(
      painter = painterResource(Res.drawable.fiesta_global_date_banner),
      contentDescription = "Montefabbri landscape",
      modifier = Modifier
        .fillMaxWidth(),
      contentScale = ContentScale.FillBounds
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      EventsSwimlane(onEventsClick)
      ArtistsSwimlane(onArtistsClick)

    }
  }
}

@Composable
private fun EventsSwimlane(
  onEventsClick: () -> Unit = {}
) {
  Text(
    text = "Programmazione",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(programmazioneItems) { item ->
      ProgrammazioneTile(text = item)
    }
  }

  Button(
    onClick = onEventsClick,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 12.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Vedi tutti gli eventi",
      color = Color.White,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun ArtistsSwimlane(onArtistsClick: () -> Unit) {
  Text(
    text = "Artisti",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(bottom = 8.dp)
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(hardcodedArtists.take(10)) { artist ->
      ArtistasTile(text = artist)
    }
  }

  Button(
    onClick = onArtistsClick,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 12.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Vedi tutti gli artisti",
      color = Color.White,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
fun ProgrammazioneTile(text: String) {
  Box(
    modifier = Modifier
      .width(150.dp)
      .height(100.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(Color(249, 196, 52))
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium,
      textAlign = TextAlign.Center
    )
  }
}

val programmazioneItems = listOf(
  "Giovedì 10 Luglio",
  "Venerdì 11 Luglio",
  "Sabato 12 Luglio",
  "Domenica 13 Luglio",
  "Aperitivo Time",
  "After Party"
)

@Composable
fun ArtistasTile(text: String) {
  Box(
    modifier = Modifier
      .width(180.dp)
      .height(80.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(Color(249, 196, 52))
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      color = Color.Black,
      style = MaterialTheme.typography.bodySmall,
      fontWeight = FontWeight.Medium,
      textAlign = TextAlign.Center,
      maxLines = 2
    )
  }
}

sealed class ScreenState {
  data object Info : ScreenState()
  data object Events : ScreenState()
}

sealed class ProfileScreenState {
  data object Main : ProfileScreenState()
  data object ArtistsDetail : ProfileScreenState()
}