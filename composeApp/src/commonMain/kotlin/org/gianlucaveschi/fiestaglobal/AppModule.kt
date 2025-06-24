package org.gianlucaveschi.fiestaglobal

import org.gianlucaveschi.fiestaglobal.data.repository.EventRepositoryImpl
import org.gianlucaveschi.fiestaglobal.domain.repository.EventRepository
import org.koin.dsl.module

val appModule = module {
    single<EventRepository> { EventRepositoryImpl() }
}