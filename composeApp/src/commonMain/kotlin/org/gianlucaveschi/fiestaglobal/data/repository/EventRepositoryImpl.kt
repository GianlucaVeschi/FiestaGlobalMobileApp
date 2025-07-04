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
            println("EventRepository: Fetching events from API...")
            
            val eventsListResponse = fetchEvents()
            
            println("EventRepository: API response received")
            
            // Log some sample events to see the image URLs
            eventsListResponse.schedule.take(3).forEach { daySchedule ->
                println("EventRepository: Day: ${daySchedule.day}")
                daySchedule.events.take(2).forEach { event ->
                    println("EventRepository: Event: ${event.name}, ImageURL: '${event.imageUrl}'")
                }
            }
            
            val domainEventSchedule = eventsListResponse.toDomain()
            println("EventRepository: Successfully converted to domain model")
            
            emit(Result.Success(domainEventSchedule))
//        } catch (exception: kotlinx.io.IOException) {
//            println("EventRepository: IOException: ${exception.message}")
//            emit(Result.Error(Exception("IOException error: ${exception.message}")))
        } catch (exception: Exception) {
            println("EventRepository: Unexpected error: ${exception.message}")
            exception.printStackTrace()
            emit(Result.Error(Exception("Unexpected error: ${exception.message}")))
        }
    }
}