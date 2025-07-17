package org.gianlucaveschi.fiestaglobal.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import org.gianlucaveschi.fiestaglobal.domain.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
  event: Event,
  onBackClick: () -> Unit
) {
  var scale by remember { mutableStateOf(1f) }
  
  val transformableState = rememberTransformableState { zoomChange, _, _ ->
    scale = (scale * zoomChange).coerceIn(0.5f, 3f)
  }
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 244, 229))
      .systemBarsPadding()
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("Informazioni Evento") },
          navigationIcon = {
            IconButton(onClick = onBackClick) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(255, 244, 229),
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
          )
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(255, 244, 229))
          .padding(innerPadding)
          .verticalScroll(rememberScrollState())
      ) {
        if (event.imageUrl.isNotBlank()) {
          AsyncImage(
            model = event.imageUrl,
            contentDescription = "Event image",
            modifier = Modifier
              .fillMaxSize(),
            contentScale = ContentScale.FillBounds
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Display location image if available
          val locationImageUrl = getLocationImageUrl(event.location)
          if (locationImageUrl != null) {
            SubcomposeAsyncImage(
              model = locationImageUrl,
              contentDescription = "Location ${event.location}",
              modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(end = 12.dp),
              contentScale = ContentScale.Fit,
              loading = {
                Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
                ) {
                  CircularProgressIndicator(
                    modifier = Modifier.width(25.dp).height(25.dp)
                  )
                }
              },
              error = {
                // Fallback to text if image fails to load
                Text(
                  text = event.location,
                  style = MaterialTheme.typography.titleMedium,
                  color = Color.Gray,
                  modifier = Modifier.padding(end = 12.dp)
                )
              }
            )
          }
          
          Text(
            text = event.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
          )
        }

        Text(
          text = "Orario: ${event.time}",
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        if (event.description.isNotBlank()) {
          Text(
            text = event.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // Map image
        val mapUrl = getMapUrl(event.location)
        if (mapUrl != null) {
          Text(
            text = "Mappa dell'evento",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
          
          Spacer(modifier = Modifier.height(8.dp))
          
          Box(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp)
              .transformable(state = transformableState),
            contentAlignment = Alignment.Center
          ) {
            SubcomposeAsyncImage(
              model = mapUrl,
              contentDescription = "Mappa evento location ${event.location}",
              modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                  scaleX = scale,
                  scaleY = scale
                ),
              contentScale = ContentScale.Fit,
              loading = {
                Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
                ) {
                  CircularProgressIndicator()
                }
              },
              error = {
                Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "Mappa non disponibile",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                  )
                }
              }
            )
          }
          
          Spacer(modifier = Modifier.height(16.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}
