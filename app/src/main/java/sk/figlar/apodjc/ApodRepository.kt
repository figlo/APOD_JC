package sk.figlar.apodjc

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import sk.figlar.apodjc.api.ApodApi

class ApodRepository {
    private val apodApi: ApodApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        apodApi = retrofit.create()
    }

    suspend fun getApodApiModels() = apodApi.getApodApiModels("2024-09-06")
}