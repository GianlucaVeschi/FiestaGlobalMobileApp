package org.gianlucaveschi.fiestaglobal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform