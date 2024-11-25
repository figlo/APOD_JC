package sk.figlar.apodjc

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import sk.figlar.apodjc.api.ApodApi
import sk.figlar.apodjc.api.ApodApiModel

class ApodRepository {
    private val apodApi: ApodApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        apodApi = retrofit.create()
    }

    suspend fun getApodApiModels(): List<ApodApiModel> {
//        try {
            return apodApi.getApodApiModels("2024-10-15")
//        } catch (ex: Exception) {
//            Timber.e("Failed to fetch gallery items: $ex")
//        }
    }
}