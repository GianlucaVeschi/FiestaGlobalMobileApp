package org.gianlucaveschi.fiestaglobal.ui

import ArtistDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import android.view.WindowManager
import android.app.Activity
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import org.gianlucaveschi.fiestaglobal.ui.artists.ArtistsScreen
import org.gianlucaveschi.fiestaglobal.ui.artists.ArtistsViewModel
import org.gianlucaveschi.fiestaglobal.ui.maps.ProfileScreen
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import androidx.compose.material.icons.Icons
import org.gianlucaveschi.fiestaglobal.ui.theme.FiestaGlobalTheme

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
      FiestaGlobalTheme {
        MainScreen()
      }
    }
  }
}

@Composable
fun MainScreen() {
  var selectedScreen by remember { mutableStateOf(ARTIST_SCREEN) }
  val artistsViewModel = ArtistsViewModel()
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
      NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = Color.White,
        contentColor = Color.Black
      ) {
        NavigationBarItem(
          icon = { Icon(Icons.Filled.Home, contentDescription = ARTIST_SCREEN) },
          label = { Text(ARTIST_SCREEN) },
          selected = selectedScreen == ARTIST_SCREEN,
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.Black,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
          ),
          onClick = { selectedScreen = ARTIST_SCREEN }
        )
        NavigationBarItem(
          icon = { Icon(Icons.Filled.Person, contentDescription = PROFILE_SCREEN) },
          label = { Text(PROFILE_SCREEN) },
          selected = selectedScreen == PROFILE_SCREEN,
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.Black,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
          ),
          onClick = { selectedScreen = PROFILE_SCREEN }
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

        PROFILE_SCREEN -> ProfileScreen()
      }
    }
  }
}

const val ARTIST_SCREEN = "Artists"
const val PROFILE_SCREEN = "Profile"