package org.gianlucaveschi.fiestaglobal.data.repository

import org.gianlucaveschi.fiestaglobal.data.fetchEvents
import org.gianlucaveschi.fiestaglobal.domain.mapper.toDomain
import org.gianlucaveschi.fiestaglobal.domain.model.EventSchedule
import org.gianlucaveschi.fiestaglobal.domain.repository.EventRepository

class EventRepositoryImpl : EventRepository {
    override suspend fun getEvents(): EventSchedule {
        val eventsListResponse = fetchEvents()
        return eventsListResponse.toDomain()
    }
} 