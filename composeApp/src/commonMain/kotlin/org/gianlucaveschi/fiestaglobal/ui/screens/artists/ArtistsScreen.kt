package org.gianlucaveschi.fiestaglobal.ui.screens.artists

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

val hardcodedArtists = listOf(
  "THE ANDY MACFARLANE TWO MAN ORCHESTRA",
  "ANGE",
  "BANDAKADABRA",
  "BOLOGNA BRIDGE BAND",
  "COLORI IN MUSICA",
  "COMPAGNIA 3DIDANE'",
  "COMPAGNIA POUET",
  "DUBADUBA",
  "FOLA'",
  "GAIA MA",
  "GHIGO DJ",
  "IL GIARDINO DEI COLORI",
  "IL TEATRO DELLE ISOLE",
  "JBEAT E MARINELLI",
  "K LSY",
  "KATASTROFA CLOWN",
  "LA COMPAGNIA DI VIVA EL BALL!",
  "LA DAMA DELL'ERMELLINO",
  "LAPALLAROTONDA",
  "LE SGAMBATE",
  "LION D",
  "LIVE PAINTING (writers)",
  "MA KENLUCK",
  "MOTSWAKO JAZZ COLLECTIVE feat. MIKE ROSSI",
  "OTTO PANZER",
  "PACKIM DJ",
  "PAPA MENGO SELECTOR",
  "PINAKOTEK",
  "QUETZALCOATL",
  "RAYMOND SOLFANELLI",
  "ROBERTO LUCCIARINI BLUES TRIO",
  "SENZAVOLTO",
  "SINGLE BARREL",
  "SLACK DJSET",
  "SLACK ERMANNO AND THE EX PRESIDENTS",
  "SLACK LAST KILLERS",
  "SLACK THE LINGS",
  "SOULFIRE PROJECT",
  "SWING-O-LELES",
  "VERONICA GONZALEZ",
  "VIBRA FAMILY",
  "WORABBI E LE DOVUTE PRECAUZIONI"
)