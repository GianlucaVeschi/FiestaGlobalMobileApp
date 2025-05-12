package org.gianlucaveschi.fiestaglobal.ui

import ArtistDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import android.view.Window
import android.view.WindowManager
import android.app.Activity
import android.view.View
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

import org.gianlucaveschi.fiestaglobal.ui.artists.ArtistsScreen
import org.gianlucaveschi.fiestaglobal.ui.artists.ArtistsViewModel
import org.gianlucaveschi.fiestaglobal.ui.maps.MapsScreen
import org.gianlucaveschi.fiestaglobal.ui.maps.MapsViewModel
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Make system bars (status and navigation) transparent
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )

    setContent {
      MainScreen()
    }
  }
}

@Composable
fun MainScreen() {
  var selectedScreen by remember { mutableStateOf(ARTIST_SCREEN) }
  val artistsViewModel = ArtistsViewModel()
  val viewModelB = MapsViewModel()
  val uiState by artistsViewModel.uiState.collectAsState()
  
  // For navigating to artist details
  var selectedArtist by remember { mutableStateOf<ArtistItemResponse?>(null) }

  val context = LocalContext.current
  DisposableEffect(Unit) {
    val window = (context as? Activity)?.window
    window?.apply {
      navigationBarColor = android.graphics.Color.WHITE
    }

    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, it.decorView) }
    windowInsetsController?.apply {
      // Show navigation bar and make it white
      show(WindowInsetsCompat.Type.navigationBars())
      isAppearanceLightNavigationBars =
        true  // This makes navigation bar icons dark when on light background
    }

    onDispose {}
  }

  Scaffold(
    bottomBar = {
      BottomNavigation(
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = Modifier.navigationBarsPadding(),
        elevation = 0.dp
      ) {
        BottomNavigationItem(
          icon = { Icon(Icons.Default.Home, contentDescription = ARTIST_SCREEN) },
          label = { Text(ARTIST_SCREEN) },
          selected = selectedScreen == ARTIST_SCREEN,
          onClick = { selectedScreen = ARTIST_SCREEN }
        )
        BottomNavigationItem(
          icon = { Icon(Icons.Default.Person, contentDescription = MAPS_SCREEN) },
          label = { Text(MAPS_SCREEN) },
          selected = selectedScreen == MAPS_SCREEN,
          onClick = { selectedScreen = MAPS_SCREEN }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (selectedScreen) {
        ARTIST_SCREEN ->
          if (selectedArtist != null) {
            ArtistDetailScreen(
              artist = selectedArtist!!,
              onBackClick = { selectedArtist = null }
            )
          } else {
            ArtistsScreen(
              uiModel = ArtistsUiState(
                artists = uiState.artists,
                isLoading = uiState.isLoading,
                error = uiState.error
              ),
              onRetry = { artistsViewModel.loadArtists() },
              onArtistClick = { artist -> selectedArtist = artist }
            )
          }

        MAPS_SCREEN -> MapsScreen(
          viewModel = viewModelB
        )
      }
    }
  }
}

const val ARTIST_SCREEN = "ArtistScreen"
const val MAPS_SCREEN = "MapsScreen"