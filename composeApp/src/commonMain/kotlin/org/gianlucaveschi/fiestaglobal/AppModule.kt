package org.gianlucaveschi.fiestaglobal

import org.koin.dsl.module

val appModule = module {
    single { "Hello world!" }
}