package org.gianlucaveschi.fiestaglobal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light theme colors with white as primary
private val LightColorScheme = lightColorScheme(
  primary = Color.White,
  onPrimary = Color.Black,
  primaryContainer = Color.White,
  onPrimaryContainer = Color.Black,
  secondary = Color.White,
  onSecondary = Color.Black,
  secondaryContainer = Color.White,
  onSecondaryContainer = Color.Black,
  tertiary = Color.White,
  onTertiary = Color.Black,
  tertiaryContainer = Color.White,
  onTertiaryContainer = Color.Black,
  background = Color.White,
  onBackground = Color.Black,
  surface = Color.White,
  onSurface = Color.Black
)

// Dark theme colors
private val DarkColorScheme = darkColorScheme(
  primary = Color.Black,
  onPrimary = Color.White,
  primaryContainer = Color.DarkGray,
  onPrimaryContainer = Color.White,
  secondary = Color.DarkGray,
  onSecondary = Color.White,
  secondaryContainer = Color.DarkGray,
  onSecondaryContainer = Color.White,
  tertiary = Color.DarkGray,
  onTertiary = Color.White,
  tertiaryContainer = Color.DarkGray,
  onTertiaryContainer = Color.White,
  background = Color.Black,
  onBackground = Color.White,
  surface = Color.Black,
  onSurface = Color.White
)

@Composable
fun FiestaGlobalTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) {
    DarkColorScheme
  } else {
    LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    content = content
  )
}