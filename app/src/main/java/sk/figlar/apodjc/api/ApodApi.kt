package sk.figlar.apodjc.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "ejIN7M9Yj2pqdvwYLGQlEC2rYaCbhDltzkjQcVj1"

interface ApodApi {
    @GET("/")
    suspend fun fetchContents() : String

    @GET("planetary/apod?api_key=$API_KEY")
    suspend fun getApodApiModels(@Query("start_date") startDate: String): List<ApodApiModel>
}