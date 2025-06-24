package org.gianlucaveschi.fiestaglobal.domain.repository

import org.gianlucaveschi.fiestaglobal.domain.model.EventSchedule

interface EventRepository {
    suspend fun getEvents(): EventSchedule
} 