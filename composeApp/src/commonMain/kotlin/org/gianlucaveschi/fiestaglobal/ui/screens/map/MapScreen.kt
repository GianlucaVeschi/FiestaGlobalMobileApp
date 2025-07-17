package org.gianlucaveschi.fiestaglobal.ui.screens.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import fiestaglobalmobileapp.composeapp.generated.resources.Res
import fiestaglobalmobileapp.composeapp.generated.resources.map
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
  title: String = "Mappa",
  onBackClick: () -> Unit
) {
  var scale by remember { mutableStateOf(1f) }
  var offsetX by remember { mutableStateOf(0f) }
  var offsetY by remember { mutableStateOf(0f) }
  
  val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
    scale = (scale * zoomChange).coerceIn(0.5f, 3f)
    offsetX += panChange.x
    offsetY += panChange.y
  }
  
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 251, 229))
      .systemBarsPadding()
  ) {
    TopAppBar(
      title = {
        Text(
          text = title,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold
        )
      },
      navigationIcon = {
        IconButton(onClick = onBackClick) {
          Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
          )
        }
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(255, 251, 229)
      )
    )
    
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .transformable(state = transformableState),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(Res.drawable.map),
          contentDescription = "Map",
          modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
              scaleX = scale,
              scaleY = scale,
              translationX = offsetX,
              translationY = offsetY
            ),
          contentScale = ContentScale.Fit
        )
      }
    }
  }
}