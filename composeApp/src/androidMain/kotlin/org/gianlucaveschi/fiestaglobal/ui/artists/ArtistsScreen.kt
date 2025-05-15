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
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState

@Composable
fun ArtistsScreen(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit,
  onArtistClick: (ArtistItemResponse) -> Unit = {}
) {
  val tabTitles = listOf(THURSDAY_TAB, FRIDAY_TAB, SATURDAY_TAB, SUNDAY_TAB)
  val pagerState = rememberPagerState { tabTitles.size }
  val coroutineScope = rememberCoroutineScope()

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
        TabRowDefaults.Indicator(
          Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
          color = Color.Black
        )
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
      when (page) {
        0 -> ArtistsContent(uiModel, onRetry, onArtistClick)
        1 -> ArtistsContent(uiModel, onRetry, onArtistClick) // TODO : Pass subset of data
        2 -> ArtistsContent(uiModel, onRetry, onArtistClick) // TODO : Pass subset of data
        3 -> ArtistsContent(uiModel, onRetry, onArtistClick) // TODO : Pass subset of data
      }
    }
  }
}

@Composable
fun ArtistsContent(
  uiModel: ArtistsUiState,
  onRetry: () -> Unit,
  onArtistClick: (ArtistItemResponse) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    when {
      uiModel.isLoading -> {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      }

      uiModel.error != null -> {
        Column(
          modifier = Modifier.align(Alignment.Center),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("Error: ${uiModel.error}")
          Spacer(modifier = Modifier.height(16.dp))
          Button(onClick = onRetry) {
            Text("Retry")
          }
        }
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp)
        ) {
          items(uiModel.artists) { artist ->
            ArtistItem(
              artist = artist,
              onClick = { onArtistClick(artist) }
            )
          }
        }
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
            text = "Subtitle or description",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
          )
        }
        Column(
          horizontalAlignment = Alignment.End
        ) {
          Text(
            text = "Day",
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
      artists = listOf(
        ArtistItemResponse(
          name = "Laboratori artistici e creativi per bambini a cura di TEATRO DELLE ISOLE",
          time = "18:00"
        ),
        ArtistItemResponse(
          name = "Apertura Mostra Foto e Video “Fiesta Global - 20 anni!”",
          time = "18:00"
        ),
        ArtistItemResponse(
          name = "Giochi di una volta e Laboratori a tema con la Capretta Cleopatra a cura di IL GIARDINO DEI COLORI",
          time = "19:00"
        ),
        ArtistItemResponse(
          name = "RAFFAELE DI PLACIDO presenta “L’uomo che uccise Mussolini” (Piemme, 2024)",
          time = "19:00"
        ),
      ),
      isLoading = false,
      error = null
    ),
    onRetry = {},
    onArtistClick = {}
  )
}

private const val THURSDAY_TAB = "GIO 17"
private const val FRIDAY_TAB = "VEN 18"
private const val SATURDAY_TAB = "SAB 19"
private const val SUNDAY_TAB = "DOM 20"