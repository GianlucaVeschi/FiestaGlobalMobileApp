package org.gianlucaveschi.fiestaglobal.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
  title: String,
  onBackClick: () -> Unit
) {
  Scaffold(
    modifier = Modifier.systemBarsPadding(),
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
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(hardcodedArtists) { artist ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Text(
            text = artist,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
          )
        }
      }
    }
  }
}

val hardcodedArtists = listOf(
  "BANDAKADABRA",
  "BOLOGNA BRIDGE BAND",
  "COLORI IN MUSICA",
  "COMPAGNIA POUET",
  "COMPAGNIA TREDIDANE'",
  "DUBADUBA",
  "FOLA'",
  "GAIA MA",
  "GHIGO DJ",
  "IL GIARDINO DEI COLORI",
  "IL TEATRO DELLE ISOLE",
  "JBEAT E MARINELLI",
  "K SLY",
  "KATASTROFA",
  "LA COMPAGNIA DI VIVA EL BALL!",
  "LA DAMA CON L'ERMELLINO",
  "LAPALLAROTONDA",
  "LE SGAMBATE",
  "LION D",
  "LUCCIARINI TRIO",
  "MA KENLUCK",
  "MENGO DJ",
  "MOTSWAKO JAZZ COLLECTIVE",
  "PACKIM DJ",
  "PINAKOTEK",
  "POETRY IN THE STREET",
  "QUETZALCOATL",
  "RAYMOND SOLFANELLI",
  "SENZA VOLTO",
  "SINGLE BARREL",
  "SLACK DJSET",
  "SLACK ERMANNO AND THE EX PRESIDENTS",
  "SLACK LAST KILLERS",
  "SLACK THE LINGS",
  "SOULFIRE PROJECT",
  "SWING O LELES",
  "THE ANGE",
  "TWO MEN ORCHESTRA",
  "VERONICA GONZALEZ",
  "VIBRA FAMILY",
  "WORABBI E LE DOVUTE PRECAUZIONI"
)