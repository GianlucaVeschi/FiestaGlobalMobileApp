import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.gianlucaveschi.fiestaglobal.R
import org.gianlucaveschi.fiestaglobal.data.model.EventItemResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
  event: EventItemResponse,
  onBackClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding()
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("Event Details") },
          navigationIcon = {
            IconButton(onClick = onBackClick) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
          )
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .verticalScroll(rememberScrollState())
      ) {
        Image(
          painter = painterResource(id = R.drawable.fiesta_global_placeholder),
          contentDescription = "Event image",
          modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
          contentScale = ContentScale.FillBounds
        )

        Text(
          text = event.name,
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(16.dp)
        )

        Text(
          text = "Time: ${event.time}",
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description (placeholder text since we don't have real descriptions)
        Text(
          text = "This is a detailed description of the artist or event. " +
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quam velit, " +
              "vulputate eu pharetra nec, mattis ac neque. Duis vulputate commodo lectus, " +
              "ac blandit elit tincidunt id. Sed rhoncus, tortor sed eleifend tristique, " +
              "tortor mauris molestie elit, et lacinia ipsum quam nec dui. Quisque nec mauris " +
              "sit amet elit iaculis pretium sit amet quis magna. Aenean velit odio, elementum " +
              "in tempus ut, vehicula eu diam. Pellentesque rhoncus aliquam mattis. " +
              "Ut vulputate eros sed felis sodales nec vulputate justo hendrerit. " +
              "Vivamus varius pretium ligula, a aliquam odio euismod sit amet.",
          style = MaterialTheme.typography.bodyLarge,
          textAlign = TextAlign.Justify,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}
