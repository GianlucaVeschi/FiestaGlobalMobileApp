package org.gianlucaveschi.fiestaglobal.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistItemResponse(
    val name: String,
    val time: String
)

@Serializable
data class ArtistsListResponse(
    val artists: List<ArtistItemResponse>
)
