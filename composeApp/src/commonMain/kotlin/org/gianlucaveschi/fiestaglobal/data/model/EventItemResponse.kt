package org.gianlucaveschi.fiestaglobal.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class EventItemResponse(
  @SerialName("name")
  val name: String,
  @SerialName("time")
  val time: String,
  @SerialName("location")
  val location: String,
)

@Serializable
data class DaySchedule(
  @SerialName("day")
  val day: String,
  @SerialName("artists")
  val events: List<EventItemResponse>
)

@Serializable
data class EventsListResponse(
  @SerialName("schedule")
  val schedule: List<DaySchedule>
)