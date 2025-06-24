package org.gianlucaveschi.fiestaglobal.ui

import EventDetailScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.MainViewModel
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventsScreen
import org.gianlucaveschi.fiestaglobal.ui.screens.profile.ProfileScreen


@Composable
fun MainScreen() {
  var selectedScreen by remember { mutableStateOf(EVENTS_SCREEN) }
  val mainViewModel = MainViewModel()
  val uiState by mainViewModel.uiState.collectAsState()

  var selectedEvents by remember { mutableStateOf<Event?>(null) }

//  val context = LocalContext.current
//  DisposableEffect(Unit) {
//    val window = (context as? Activity)?.window
//    window?.apply {
//      navigationBarColor = android.graphics.Color.WHITE
//    }
//
//    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, it.decorView) }
//    windowInsetsController?.apply {
//      // Show navigation bar and make it white
//      show(WindowInsetsCompat.Type.navigationBars())
//      isAppearanceLightNavigationBars =
//        true  // This makes navigation bar icons dark when on light background
//    }
//
//    onDispose {}
//  }

  Scaffold(
    bottomBar = {
      NavigationBar(
        modifier = Modifier
          .navigationBarsPadding()
          .height(64.dp),
        containerColor = Color.White,
        contentColor = Color.Black
      ) {
        NavigationBarItem(
          icon = { Icon(Icons.Filled.Home, contentDescription = EVENTS_SCREEN) },
          label = { androidx.compose.material3.Text(EVENTS_SCREEN) },
          selected = selectedScreen == EVENTS_SCREEN,
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.Black,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
          ),
          onClick = { selectedScreen = EVENTS_SCREEN }
        )
        NavigationBarItem(
          icon = { Icon(Icons.Filled.Person, contentDescription = PROFILE_SCREEN) },
          label = { androidx.compose.material3.Text(PROFILE_SCREEN) },
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
        EVENTS_SCREEN ->
          if (selectedEvents != null) {
            EventDetailScreen(
              event = selectedEvents!!,
              onBackClick = { selectedEvents = null }
            )
          } else {
            EventsScreen(
              uiModel = EventsUiState(
                daySchedules = uiState.daySchedules,
                isLoading = uiState.isLoading,
                error = uiState.error
              ),
              onRetry = { mainViewModel.loadEvents() },
              onEventClick = {
                // TODO: Navigation to detail screen is disabled as there is no real data yet
                //  event -> selectedEvents = event
              }
            )
          }

        PROFILE_SCREEN -> ProfileScreen()
      }
    }
  }
}


const val EVENTS_SCREEN = "Eventi"
const val PROFILE_SCREEN = "Profilo"