package sk.figlar.apodjc.data

import retrofit2.http.GET
import retrofit2.http.Query
import sk.figlar.apodjc.model.ApodApiModel

private const val API_KEY = "ejIN7M9Yj2pqdvwYLGQlEC2rYaCbhDltzkjQcVj1"

interface ApodApi {
    @GET("planetary/apod?api_key=$API_KEY")
    suspend fun getApodApiModels(@Query("start_date") startDate: String): List<ApodApiModel>
}