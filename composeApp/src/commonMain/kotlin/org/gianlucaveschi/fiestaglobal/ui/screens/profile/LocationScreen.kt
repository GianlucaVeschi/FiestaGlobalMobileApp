package org.gianlucaveschi.fiestaglobal.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
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
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
        text = comeArrivare,
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

const val comeArrivare = "Dall'autostrada A14:\n" +
    "\n" +
    "Esci al casello di Pesaro-Urbino.\n" +
    "Dopo l'uscita, segui le indicazioni per la SP423 in direzione Urbino.\n" +
    "Percorri la SP423 per circa 15 km fino a trovare il bivio per Montefabbri. Il borgo è ben segnalato lungo il percorso.\n" +
    "Da Urbino:\n" +
    "\n" +
    "Prendi la SP423 in direzione Pesaro.\n" +
    "Dopo circa 10 km, troverai le indicazioni per Montefabbri. Segui il bivio e prosegui per pochi chilometri.\n" +
    "Montefabbri è facilmente raggiungibile tramite strade provinciali ben mantenute. Ti consiglio di prestare attenzione ai cartelli stradali, poiché il borgo è piccolo e potrebbe non apparire subito visibile."