package sk.figlar.apodjc.api

import com.squareup.moshi.Json

data class ApodApiModel(
    val copyright: String,
    val date: String,
    val explanation: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String,
)