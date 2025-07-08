package org.gianlucaveschi.fiestaglobal.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import org.gianlucaveschi.fiestaglobal.ui.screens.MainScreen
import org.gianlucaveschi.fiestaglobal.ui.theme.FiestaGlobalTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    // Make system bars (status and navigation) transparent
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )

    setContent {
      FiestaGlobalTheme {
        MainScreen()
      }
    }
  }
}