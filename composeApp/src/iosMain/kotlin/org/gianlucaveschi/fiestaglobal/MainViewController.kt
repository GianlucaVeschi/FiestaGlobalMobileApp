package org.gianlucaveschi.fiestaglobal

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
  configure = {
    KoinInitializer().init()
  }
) {
  App()
}