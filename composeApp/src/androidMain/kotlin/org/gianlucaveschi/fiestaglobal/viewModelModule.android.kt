package org.gianlucaveschi.fiestaglobal

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
  viewModelOf(::MainViewModel)
}