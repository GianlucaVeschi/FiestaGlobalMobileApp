package org.gianlucaveschi.fiestaglobal.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import org.gianlucaveschi.fiestaglobal.data.model.EventsListResponse

suspend fun fetchEvents(): EventsListResponse {
  val client = httpClientAndroid
  return client.get("https://fiestaglobalapp.web.app/data_with_images.json").body()
}
