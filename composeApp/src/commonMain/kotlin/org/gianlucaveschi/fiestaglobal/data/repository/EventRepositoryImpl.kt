package org.gianlucaveschi.fiestaglobal.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.gianlucaveschi.fiestaglobal.data.fetchEvents
import org.gianlucaveschi.fiestaglobal.domain.mapper.toDomain
import org.gianlucaveschi.fiestaglobal.domain.model.EventSchedule
import org.gianlucaveschi.fiestaglobal.domain.model.Result
import org.gianlucaveschi.fiestaglobal.domain.repository.EventRepository

class EventRepositoryImpl : EventRepository {
    override fun getEvents(): Flow<Result<EventSchedule>> = flow {
        emit(Result.Loading)
        try {
            val eventsListResponse = fetchEvents()
            val domainEventSchedule = eventsListResponse.toDomain()
            emit(Result.Success(domainEventSchedule))
        } catch (exception: kotlinx.io.IOException) {
            emit(Result.Error(Exception("IOException error: ${exception.message}")))
        } catch (exception: Exception) {
            emit(Result.Error(Exception("Unexpected error: ${exception.message}")))
        }
    }
}