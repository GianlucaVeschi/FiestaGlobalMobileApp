package org.gianlucaveschi.fiestaglobal

import android.app.Application


class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}