package org.gianlucaveschi.fiestaglobal.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EventItemResponse(
    val name: String,
    val time: String,
    val location: String,
)

@Serializable
data class DaySchedule(
    val day: String,
    val events: List<EventItemResponse>
)

@Serializable
data class EventsListResponse(
    val schedule: List<DaySchedule>
)