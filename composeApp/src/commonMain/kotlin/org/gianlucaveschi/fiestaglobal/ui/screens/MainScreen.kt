package org.gianlucaveschi.fiestaglobal.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fiestaglobalmobileapp.composeapp.generated.resources.Res
import fiestaglobalmobileapp.composeapp.generated.resources._17
import fiestaglobalmobileapp.composeapp.generated.resources._18
import fiestaglobalmobileapp.composeapp.generated.resources._19
import fiestaglobalmobileapp.composeapp.generated.resources._20
import fiestaglobalmobileapp.composeapp.generated.resources.facebook
import fiestaglobalmobileapp.composeapp.generated.resources.instagram
import fiestaglobalmobileapp.composeapp.generated.resources.new_banner
import fiestaglobalmobileapp.composeapp.generated.resources.youtube
import org.gianlucaveschi.fiestaglobal.MainViewModel
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.gianlucaveschi.fiestaglobal.ui.screens.artists.ArtistsScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventDetailScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventsScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.food.FoodScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.map.MapScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.parking.ParkingScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.directions.DirectionsScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
  var currentScreen by remember { mutableStateOf<ScreenState>(ScreenState.Info) }
  val mainViewModel: MainViewModel = koinInject()
  val uiState by mainViewModel.uiState.collectAsState()

  var selectedEvents by remember { mutableStateOf<Event?>(null) }
  var selectedTabIndex by remember { mutableStateOf(0) }

  val pagerState = when (val currentUiState = uiState) {
    is EventsUiState.Success -> rememberPagerState { currentUiState.daySchedules.size }
    else -> null
  }

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = {
      if (targetState is ScreenState.Events && initialState is ScreenState.Info) {
        // Slide in from right when going to Events
        slideInHorizontally(initialOffsetX = { it }) togetherWith
            slideOutHorizontally(targetOffsetX = { -it })
      } else if (targetState is ScreenState.Info && initialState is ScreenState.Events) {
        // Slide in from left when going back to Info
        slideInHorizontally(initialOffsetX = { -it }) togetherWith
            slideOutHorizontally(targetOffsetX = { it })
      } else {
        // Default transition
        slideInHorizontally() togetherWith slideOutHorizontally()
      }
    }
  ) { screen ->
    when (screen) {
      is ScreenState.Info -> {
        var profileScreen by remember { mutableStateOf<ProfileScreenState>(ProfileScreenState.Main) }

        AnimatedContent(
          targetState = profileScreen,
          transitionSpec = {
            if ((targetState is ProfileScreenState.ArtistsDetail || targetState is ProfileScreenState.FoodDetail || targetState is ProfileScreenState.MapDetail || targetState is ProfileScreenState.ParkingDetail || targetState is ProfileScreenState.DirectionsDetail) && initialState is ProfileScreenState.Main) {
              // Slide in from right when going to Artists, Food, Map, Parking, or Directions
              slideInHorizontally(initialOffsetX = { it }) togetherWith
                  slideOutHorizontally(targetOffsetX = { -it })
            } else if (targetState is ProfileScreenState.Main && (initialState is ProfileScreenState.ArtistsDetail || initialState is ProfileScreenState.FoodDetail || initialState is ProfileScreenState.MapDetail || initialState is ProfileScreenState.ParkingDetail || initialState is ProfileScreenState.DirectionsDetail)) {
              // Slide in from left when going back to Main
              slideInHorizontally(initialOffsetX = { -it }) togetherWith
                  slideOutHorizontally(targetOffsetX = { it })
            } else {
              // Default transition
              slideInHorizontally() togetherWith slideOutHorizontally()
            }
          }
        ) { profileState ->
          when (profileState) {
            is ProfileScreenState.Main -> MainProfileScreen(
              onArtistsClick = { profileScreen = ProfileScreenState.ArtistsDetail },
              onEventsClick = { tabIndex ->
                selectedTabIndex = tabIndex
                currentScreen = ScreenState.Events
              },
              onCiboClick = { profileScreen = ProfileScreenState.FoodDetail },
              onMapClick = { profileScreen = ProfileScreenState.MapDetail },
              onParkingClick = { profileScreen = ProfileScreenState.ParkingDetail },
              onDirectionsClick = { profileScreen = ProfileScreenState.DirectionsDetail }
            )

            is ProfileScreenState.ArtistsDetail -> ArtistsScreen(
              title = "Artisti & Band",
              onBackClick = { profileScreen = ProfileScreenState.Main }
            )

            is ProfileScreenState.FoodDetail -> FoodScreen(
              title = "Cibo",
              onBackClick = { profileScreen = ProfileScreenState.Main }
            )

            is ProfileScreenState.MapDetail -> MapScreen(
              title = "Mappa",
              onBackClick = { profileScreen = ProfileScreenState.Main }
            )

            is ProfileScreenState.ParkingDetail -> ParkingScreen(
              title = "Parcheggio",
              onBackClick = { profileScreen = ProfileScreenState.Main }
            )

            is ProfileScreenState.DirectionsDetail -> DirectionsScreen(
              title = "Come Arrivare",
              onBackClick = { profileScreen = ProfileScreenState.Main }
            )
          }
        }
      }

      is ScreenState.Events -> {
        AnimatedContent(
          targetState = selectedEvents,
          transitionSpec = {
            if (targetState != null && initialState == null) {
              // Slide in from right when going to Event Detail
              slideInHorizontally(initialOffsetX = { it }) togetherWith
                  slideOutHorizontally(targetOffsetX = { -it })
            } else if (targetState == null && initialState != null) {
              // Slide in from left when going back to Events List
              slideInHorizontally(initialOffsetX = { -it }) togetherWith
                  slideOutHorizontally(targetOffsetX = { it })
            } else {
              // Default transition
              slideInHorizontally() togetherWith slideOutHorizontally()
            }
          }
        ) { selectedEvent ->
          if (selectedEvent != null) {
            EventDetailScreen(
              event = selectedEvent,
              onBackClick = { selectedEvents = null }
            )
          } else {
            EventsScreen(
              uiState = uiState,
              onRetry = { mainViewModel.loadEvents() },
              onEventClick = { event ->
                selectedEvents = event
              },
              pagerState = pagerState,
              onBackClick = { currentScreen = ScreenState.Info },
              initialTabIndex = selectedTabIndex,
              onTabChanged = { tabIndex ->
                selectedTabIndex = tabIndex
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun MainProfileScreen(
  onArtistsClick: () -> Unit,
  onEventsClick: (Int) -> Unit = {},
  onCiboClick: () -> Unit = {},
  onMapClick: () -> Unit = {},
  onParkingClick: () -> Unit = {},
  onDirectionsClick: () -> Unit = {}
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 251, 229))
      .systemBarsPadding()
      .verticalScroll(scrollState)
  ) {
    Image(
      painter = painterResource(Res.drawable.new_banner),
      contentDescription = "Montefabbri landscape",
      modifier = Modifier
        .height(400.dp)
        .fillMaxWidth(),
      contentScale = ContentScale.FillBounds
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      EventsSwimlane { tabIndex -> onEventsClick(tabIndex) }
      ArtistsSwimlane(onArtistsClick)
      MapsSwimlane(onMapClick)
      FoodSwimlane(onCiboClick)
      DirectionsSwimlane(onDirectionsClick)
      ParkingSwimlane(onParkingClick)
      SocialSwimlane()
      
      Text(
        text = "Made with ❤️ in Berlin - v1.5.0",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun EventsSwimlane(
  onEventsClick: (Int) -> Unit,
) {
  Text(
    text = "🎭 Programmazione",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(4) { index ->
      EventTile(
        text = "",
        imageIndex = index,
        onClick = { onEventsClick(index) }
      )
    }
  }

  Button(
    onClick = { onEventsClick(0) },
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Tutti gli Eventi",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun MapsSwimlane(
  onMapClick: () -> Unit
) {
  Text(
    text = "🗺️ Mappa",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  Button(
    onClick = onMapClick,
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Vedi Mappa",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun ArtistsSwimlane(
  onArtistsClick: () -> Unit
) {
  Text(
    text = "🎤 Artisti",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(hardcodedArtists) { artist ->
      ArtistsTiles(text = artist)
    }
  }

  Button(
    onClick = onArtistsClick,
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Tutti gli Artisti",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun ParkingSwimlane(
  onParkingClick: () -> Unit
) {
  Text(
    text = "\uD83C\uDD7F\uFE0F Parcheggio",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  Button(
    onClick = onParkingClick,
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Dove parcheggiare",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun DirectionsSwimlane(
  onDirectionsClick: () -> Unit
) {
  Text(
    text = "🚗 Come Arrivare",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  Button(
    onClick = onDirectionsClick,
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Come Arrivare",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun SocialSwimlane() {
  val uriHandler = LocalUriHandler.current

  Text(
    text = "📱 Social",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(3) { index ->
      SocialImageTile(
        socialIndex = index,
        onClick = { url ->
          uriHandler.openUri(url)
        }
      )
    }
  }
}

@Composable
fun SocialImageTile(
  socialIndex: Int,
  onClick: (String) -> Unit = {}
) {
  val imageResource = when (socialIndex) {
    0 -> Res.drawable.facebook
    1 -> Res.drawable.instagram
    2 -> Res.drawable.youtube
    else -> Res.drawable.facebook
  }

  val socialUrl = when (socialIndex) {
    0 -> "https://www.facebook.com/fiestaglobal"
    1 -> "https://www.instagram.com/fiesta_global_off/"
    2 -> "https://www.youtube.com/@tribaleggs"
    else -> "https://www.facebook.com/fiestaglobal"
  }

  Image(
    painter = painterResource(imageResource),
    contentDescription = null,
    modifier = Modifier
      .size(80.dp)
      .clip(CircleShape)
      .clickable { onClick(socialUrl) },
    contentScale = ContentScale.Fit
  )
}

@Composable
fun EventTile(
  text: String,
  imageIndex: Int,
  onClick: () -> Unit = {}
) {
  val imageResource = when (imageIndex) {
    0 -> Res.drawable._17
    1 -> Res.drawable._18
    2 -> Res.drawable._19
    3 -> Res.drawable._20
    else -> Res.drawable._19
  }

  Image(
    painter = painterResource(imageResource),
    contentDescription = null,
    modifier = Modifier
      .width(100.dp)
      .height(100.dp)
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick),
    contentScale = ContentScale.Fit
  )
}

@Composable
private fun FoodSwimlane(
  onFoodClick: () -> Unit
) {
  Text(
    text = "🍕 Food",
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
  )

  LazyRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(hardcodedFood) { food ->
      FoodTiles(foodItem = food)
    }
  }

  Button(
    onClick = onFoodClick,
    modifier = Modifier
      .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(255, 165, 0)
    ),
    shape = RoundedCornerShape(24.dp)
  ) {
    Text(
      text = "Tutto il cibo",
      color = Color.Black,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
fun FoodTiles(foodItem: FoodItem) {
  Box(
    modifier = Modifier
      .width(180.dp)
      .height(80.dp)
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
      )
      .clip(RoundedCornerShape(8.dp))
      .background(Color(249, 196, 52))
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = foodItem.name,
      color = Color.Black,
      style = MaterialTheme.typography.bodySmall,
      fontWeight = FontWeight.Medium,
      textAlign = TextAlign.Center,
      maxLines = 2
    )
  }
}


@Composable
fun ArtistsTiles(text: String) {
  Box(
    modifier = Modifier
      .width(180.dp)
      .height(80.dp)
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
      )
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
  data object FoodDetail : ProfileScreenState()
  data object MapDetail : ProfileScreenState()
  data object ParkingDetail : ProfileScreenState()
  data object DirectionsDetail : ProfileScreenState()
}