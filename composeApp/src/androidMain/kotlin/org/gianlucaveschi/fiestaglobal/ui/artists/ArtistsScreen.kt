package org.gianlucaveschi.fiestaglobal.ui.artists

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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
          SecondaryIndicator(
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
    ) { page ->
      if (page < uiModel.daySchedules.size) {
        ArtistsContent(
          artists = uiModel.daySchedules[page].artists,
          onRetry = onRetry,
          onArtistClick = onArtistClick
        )
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
  val artistsByTime = artists.groupBy { it.time }

  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
      artistsByTime.forEach { (time, artistsAtTime) ->
        item {
          TimeHeader(time = time)
        }
        items(artistsAtTime) { artist ->
          ArtistItem(
            artist = artist,
            onClick = { onArtistClick(artist) }
          )
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
            text = artist.name,
            style = MaterialTheme.typography.titleLarge
          )
          Text(
            text = "Palco - ${artist.location}",
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
  ArtistsScreen(
    uiModel = ArtistsUiState(
      daySchedules = listOf(
        DaySchedule(
          day = "Giovedì",
          artists = listOf(
            ArtistItemResponse(
              name = "Laboratori artistici e creativi per bambini",
              time = "18:00",
              location = "Teatro delle isole"
            ),
            ArtistItemResponse(
              name = "Apertura Mostra Foto e Video",
              time = "18:00",
              location = "SOTTOMURA STAGE"
            ),
            ArtistItemResponse(
              name = "Giochi di una volta",
              time = "19:00",
              location = "Teatro delle isole"
            ),
            ArtistItemResponse(
              name = "Concerto Jazz",
              time = "19:00",
              location = "SOTTOMURA STAGE"
            ),
            ArtistItemResponse(
              name = "Spettacolo teatrale",
              time = "21:00",
              location = "Teatro delle isole"
            )
          )
        ),
        DaySchedule(
          day = "Venerdì",
          artists = listOf(
            ArtistItemResponse(
              name = "Workshop di pittura",
              time = "17:00",
              location = "Teatro delle isole"
            ),
            ArtistItemResponse(
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
    onArtistClick = {}
  )
}
