package org.gianlucaveschi.fiestaglobal.ui.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.gianlucaveschi.fiestaglobal.R
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import org.gianlucaveschi.fiestaglobal.data.model.DaySchedule
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState

@Composable
fun ArtistsScreen(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit,
  onArtistClick: (ArtistItemResponse) -> Unit = {}
) {
  val tabTitles = uiModel.daySchedules.map { it.day }
  val pagerState = rememberPagerState { tabTitles.size }
  val coroutineScope = rememberCoroutineScope()

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
      .systemBarsPadding()
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

    HorizontalPager(
      state = pagerState,
      modifier = Modifier
        .weight(1f)
        .systemBarsPadding()
    ) { page ->
      if (page < uiModel.daySchedules.size) {
        ArtistsContent(uiModel.daySchedules[page].artists, onRetry, onArtistClick)
      }
    }
  }
}

@Composable
fun ArtistsContent(
  artists: List<ArtistItemResponse>,
  onRetry: () -> Unit,
  onArtistClick: (ArtistItemResponse) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(16.dp)
    ) {
      items(artists) { artist ->
        ArtistItem(
          artist = artist,
          onClick = { onArtistClick(artist) }
        )
      }
    }
  }
}

@Composable
fun ArtistItem(
  artist: ArtistItemResponse,
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
      Image(
        painter = painterResource(id = R.drawable.fiesta_global_placeholder),
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .height(150.dp),
        contentScale = ContentScale.Crop
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = artist.name,
            style = MaterialTheme.typography.titleLarge
          )
          Text(
            text = "Palco - ${artist.location}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
          )
        }
        Column(
          horizontalAlignment = Alignment.End
        ) {
          Text(
            text = "Orario",
            style = MaterialTheme.typography.bodyMedium
          )
          Text(
            text = artist.time,
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }
    }
  }
}


@Preview
@Composable
fun AppAndroidPreview() {
  ArtistsScreen(
    uiModel = ArtistsUiState(
      daySchedules = listOf(
        DaySchedule(
          day = "Thursday",
          artists = listOf(
            ArtistItemResponse(
              name = "Laboratori artistici e creativi per bambini a cura di TEATRO DELLE ISOLE",
              time = "18:00",
              location = "Teatro delle isole"
            ),
            ArtistItemResponse(
              name = "Apertura Mostra Foto e Video “Fiesta Global - 20 anni!”",
              time = "18:00",
              location = "Teatro delle isole"
            )
          )
        ),
        DaySchedule(
          day = "Friday",
          artists = listOf(
            ArtistItemResponse(
              name = "Giochi di una volta e Laboratori a tema con la Capretta Cleopatra a cura di IL GIARDINO DEI COLORI",
              time = "19:00",
              location = "Teatro delle isole"
            ),
            ArtistItemResponse(
              name = "RAFFAELE DI PLACIDO presenta “L’uomo che uccise Mussolini” (Piemme, 2024)",
              time = "19:00",
              location = "Teatro delle isole"
            )
          )
        )
      ),
      isLoading = false,
      error = null
    ),
    onRetry = {},
    onArtistClick = {}
  )
}
