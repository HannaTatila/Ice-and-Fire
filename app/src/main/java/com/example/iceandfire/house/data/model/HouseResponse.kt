package com.example.iceandfire.house.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HouseResponse(
    @SerialName("coatOfArms") val coatOfArms: String?,
    @SerialName("name") val name: String?,
    @SerialName("region") val region: String?,
    @SerialName("url") val url: String?,
    @SerialName("words") val words: String?
)
