package org.gianlucaveschi.fiestaglobal.ui.screens.artists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.ui.screens.hardcodedArtists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
  title: String,
  onBackClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 244, 229))
  ) {
    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
      containerColor = Color(255, 244, 229),
      topBar = {
        TopAppBar(
          title = { Text(title) },
          navigationIcon = {
            IconButton(onClick = onBackClick) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
              )
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(255, 244, 229),
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
          )
        )
      }
    ) { paddingValues ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(255, 244, 229))
          .padding(paddingValues)
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(hardcodedArtists) { artist ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
              containerColor = Color(249, 196, 52)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Text(
              text = artist,
              style = MaterialTheme.typography.bodyLarge,
              color = Color.Black,
              modifier = Modifier.padding(16.dp)
            )
          }
        }
      }
    }
  }
}