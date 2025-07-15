package org.gianlucaveschi.fiestaglobal.ui.screens.events

private const val imageUrl_base_number = "https://firebasestorage.googleapis.com/v0/b/fiestaglobalapp.firebasestorage.app/o/numbers%2F"

private val imageUrlTokens = mapOf(
    "0" to "41e11ed2-6f30-43af-8367-66e54e76d319",
    "1" to "d56ac27f-510d-40f5-ba5b-69038bdffb70",
    "2" to "f7e0c85a-d3e8-4c82-bce9-c096510d7174",
    "3" to "1b821abb-53e9-43f7-b463-2ac2e800ebaf",
    "4" to "8e8ba916-acc9-418f-9f7e-52c48228d7f1",
    "5" to "d4a5df5a-f3a0-4fa4-acf9-e5c7bc2ca145",
    "6" to "83fa1bf1-4d06-4754-80fc-ea567892ff16",
    "7" to "7ea33ddf-b643-436a-9f25-e57b489fcac6",
    "8" to "2e4ee48d-e6db-486c-88d0-efc653828727",
    "9" to "73e5d007-a358-4b55-99e4-3352762d7490",
    "10" to "1de525ed-3081-4f11-bb75-185b32c62fd6"
)

private const val mapUrl_base = "https://firebasestorage.googleapis.com/v0/b/fiestaglobalapp.firebasestorage.app/o/maps%2F"

private val mapUrlData = mapOf(
    "0" to ("mappa1.png" to "c9e0f5ff-9947-4825-b8db-fd8ed9b4adf7"),
    "1" to ("mappa2.png" to "394253b2-6642-4553-a8ff-2bc4f3a6dc0b"),
    "2" to ("mappa2.png" to "394253b2-6642-4553-a8ff-2bc4f3a6dc0b"),
    "3" to ("mappa3.png" to "2e6da88f-51b4-4d8f-9677-b75f80058dcb"),
    "6" to ("mappa6.png" to "de15be6b-8cdd-4755-a8d9-e87d550231ef"),
    "7" to ("mappa7.png" to "f6d4cf32-3d28-433b-bb01-74e453654274"),
    "8" to ("mappa8.png" to "17587142-a9d1-4bf3-a0d5-d358e482a17d"),
    "9" to ("mappa9.png" to "dbdbd1a3-1bd0-4150-8397-3ac78dcae400"),
    "10" to ("mappa10.png" to "8a8dc8de-4336-4c86-9e75-9efa083a6189")
)


fun getLocationImageUrl(location: String): String? {
  return imageUrlTokens[location]?.let { token ->
    "${imageUrl_base_number}${location}.png?alt=media&token=${token}"
  }
}

fun getMapUrl(location: String): String? {
  return mapUrlData[location]?.let { (filename, token) ->
    "${mapUrl_base}${filename}?alt=media&token=${token}"
  }
}