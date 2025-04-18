package org.gianlucaveschi.fiestaglobal.data

import httpClientAndroid
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.gianlucaveschi.fiestaglobal.data.model.ArtistsListResponse

suspend fun fetchArtists(): ArtistsListResponse {
  val client = httpClientAndroid
  return client.use {
    it.get("https://simple-heroku-api-c85d9dd518e3.herokuapp.com/api/timetable").body()
  }
}
