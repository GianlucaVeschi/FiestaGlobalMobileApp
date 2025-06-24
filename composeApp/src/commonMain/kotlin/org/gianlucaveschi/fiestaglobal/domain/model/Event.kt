package org.gianlucaveschi.fiestaglobal.domain.model

data class Event(
    val name: String,
    val time: String,
    val location: String,
    val id: String = "${name}_${time}_${location}".hashCode().toString()
)

data class DaySchedule(
    val day: String,
    val events: List<Event>
)

data class EventSchedule(
    val schedule: List<DaySchedule>
) 