package org.gianlucaveschi.fiestaglobal.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.R

@Composable
fun ProfileScreen() {
  var currentScreen by remember { mutableStateOf<ProfileScreenState>(ProfileScreenState.Main) }
  
  when (val screenState = currentScreen) {
    is ProfileScreenState.Main -> MainProfileScreen(
      onArtistsClick = { currentScreen = ProfileScreenState.ArtistsDetail },
      onLocationClick = { currentScreen = ProfileScreenState.LocationDetail },
      onPartnersClick = { currentScreen = ProfileScreenState.PartnersDetail },
      onContactClick = { currentScreen = ProfileScreenState.ContactDetail }
    )
    is ProfileScreenState.ArtistsDetail -> ArtistsScreen(
      title = "Artisti & Band",
      onBackClick = { currentScreen = ProfileScreenState.Main }
    )
    is ProfileScreenState.LocationDetail -> DetailScreen(
      title = "Find Location",
      onBackClick = { currentScreen = ProfileScreenState.Main }
    )
    is ProfileScreenState.PartnersDetail -> DetailScreen(
      title = "Partners & Sponsors",
      onBackClick = { currentScreen = ProfileScreenState.Main }
    )
    is ProfileScreenState.ContactDetail -> ContactScreen(
      title = "Contact",
      onBackClick = { currentScreen = ProfileScreenState.Main }
    )
  }
}

@Composable
fun MainProfileScreen(
  onArtistsClick: () -> Unit,
  onLocationClick: () -> Unit,
  onPartnersClick: () -> Unit,
  onContactClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Image(
      painter = painterResource(id = R.drawable.montefabbri_landscape),
      contentDescription = "Montefabbri landscape",
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
      contentScale = ContentScale.Crop
    )

    Text(
      text = "Montefabbri, Italy",
      modifier = Modifier.padding(16.dp),
      style = MaterialTheme.typography.headlineSmall
    )
    
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      ProfileCard(title = "Artisti", onClick = onArtistsClick)
      ProfileCard(title = "Find Location", onClick = onLocationClick)
      ProfileCard(title = "Partners & Sponsors", onClick = onPartnersClick)
      ProfileCard(title = "Contact", onClick = onContactClick)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
  title: String,
  onBackClick: () -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(title) },
        navigationIcon = {
          IconButton(onClick = onBackClick) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color.White,
          titleContentColor = Color.Black,
          navigationIconContentColor = Color.Black
        )
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "This is the $title detail screen",
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

@Composable
fun ProfileCard(
  title: String,
  onClick: () -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .height(80.dp)
      .clickable(onClick = onClick),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
      )
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = "Navigate to detail"
      )
    }
  }
}

sealed class ProfileScreenState {
  data object Main : ProfileScreenState()
  data object ArtistsDetail : ProfileScreenState()
  data object LocationDetail : ProfileScreenState()
  data object PartnersDetail : ProfileScreenState()
  data object ContactDetail : ProfileScreenState()
}