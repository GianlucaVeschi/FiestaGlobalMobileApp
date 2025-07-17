package org.gianlucaveschi.fiestaglobal.ui.screens.directions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionsScreen(
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
        val uriHandler = LocalUriHandler.current
        
        Text(
          text = "Ecco come arrivare a Montefabbri (PU) da Pesaro e Urbino! 😊\n\n" +
                "Da Pesaro (PU): 🏖️\n" +
                "In auto 🚗:\n\n" +
                "Prendi la SS423 in direzione Urbino.\n" +
                "Dopo circa 15 km, segui le indicazioni per Tavullia e poi per Montefabbri.\n" +
                "Il viaggio dura circa 25-30 minuti.\n\n\n" +
                "Da Urbino (PU): 🏰\n" +
                "In auto 🚗:\n\n" +
                "Prendi la SP9 in direzione Fermignano.\n" +
                "Segui le indicazioni per Montefabbri.\n" +
                "Il percorso è di circa 15 km e dura 20 minuti.\n",
          style = MaterialTheme.typography.bodyLarge,
          color = Color.Black,
          textAlign = TextAlign.Justify,
          lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
          onClick = { 
            uriHandler.openUri("https://maps.app.goo.gl/KhVzctc6ipaKBC15A")
          },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(255, 165, 0)
          ),
        ) {
          Text(
            text = "Apri in Google Maps",
            style = MaterialTheme.typography.bodyLarge
          )
        }
      }
    }
  }
}