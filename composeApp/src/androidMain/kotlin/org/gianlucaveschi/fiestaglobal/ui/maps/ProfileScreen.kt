package org.gianlucaveschi.fiestaglobal.ui.maps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.R

@Composable
fun ProfileScreen(
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
  ) {
    Image(
      painter = painterResource(id = R.drawable.montefabbri_landscape), // Replace with your actual drawable resource
      contentDescription = "Montefabbri landscape", // Provide a meaningful content description
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp), // Adjust height as needed
      contentScale = ContentScale.Crop
    )

    // Information Text
    Text(
      text = "Montefabbri, Italy", // Replace with your actual string resource
      modifier = Modifier.padding(16.dp),
      style = MaterialTheme.typography.bodySmall
    )
  }
}


