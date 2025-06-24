package org.gianlucaveschi.fiestaglobal.domain.repository

import kotlinx.coroutines.flow.Flow
import org.gianlucaveschi.fiestaglobal.domain.model.EventSchedule
import org.gianlucaveschi.fiestaglobal.domain.model.Result

interface EventRepository {
    fun getEvents(): Flow<Result<EventSchedule>>
} 