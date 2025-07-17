package org.gianlucaveschi.fiestaglobal.ui.screens.parking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(
  title: String,
  onBackClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(255, 244, 229))
      .systemBarsPadding()
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text(title) },
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
          .padding(16.dp)
      ) {
        Text(
          text = "Informazioni Parcheggio",
          style = MaterialTheme.typography.headlineMedium,
          color = Color.Black,
          modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
          text = "Il pubblico potrà parcheggiare in due ampi parcheggi allestiti nei campi 500 metri dopo il borgo di Montefabbri (strada per Urbino). I parcheggi sono serviti da navette gratuite che fanno servizio continuato e trasportano le persone fino all'ingresso del festival. Ci sarà anche un parcheggio riservato ai camper e uno dedicato ai motocicli.\n\n" +
                "‼️Attenzione a non parcheggiare nei tratti di strada con divieto di parcheggio e soprattutto nei parcheggi riservati alle persone disabili, e quelli destinati ai residenti del borgo, nei pressi dell'entrata del festival.\n\n" +
                "🪅 Buona Fiesta!",
          style = MaterialTheme.typography.bodyLarge,
          color = Color.Black,
          textAlign = TextAlign.Justify,
          lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
        )
      }
    }
  }
}