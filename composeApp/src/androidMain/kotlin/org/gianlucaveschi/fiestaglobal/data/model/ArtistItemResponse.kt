package org.gianlucaveschi.fiestaglobal.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistItemResponse(
    val name: String,
    val time: String,
    val location: String,
)

@Serializable
data class DaySchedule(
    val day: String,
    val artists: List<ArtistItemResponse>
)

@Serializable
data class ArtistsListResponse(
    val schedule: List<DaySchedule>
)