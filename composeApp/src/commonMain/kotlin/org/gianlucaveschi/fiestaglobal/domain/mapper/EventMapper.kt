package org.gianlucaveschi.fiestaglobal.domain.mapper

import org.gianlucaveschi.fiestaglobal.data.model.EventItemResponse
import org.gianlucaveschi.fiestaglobal.data.model.DaySchedule as DataDaySchedule
import org.gianlucaveschi.fiestaglobal.data.model.EventsListResponse
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.domain.model.DaySchedule
import org.gianlucaveschi.fiestaglobal.domain.model.EventSchedule

fun EventItemResponse.toDomain(): Event {
  return Event(
    name = this.name,
    time = this.time,
    location = this.location,
    imageUrl = this.imageUrl
  )
}

fun DataDaySchedule.toDomain(): DaySchedule {
  return DaySchedule(
    day = this.day,
    events = this.events.map { it.toDomain() }
  )
}

fun EventsListResponse.toDomain(): EventSchedule {
  return EventSchedule(
    schedule = this.schedule.map { it.toDomain() }
  )
} 