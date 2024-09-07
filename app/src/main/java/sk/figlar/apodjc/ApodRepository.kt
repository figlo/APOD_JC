package sk.figlar.apodjc

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import sk.figlar.apodjc.api.ApodApi

class ApodRepository {
    private val apodApi: ApodApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        apodApi = retrofit.create()
    }

    suspend fun fetchContents() = apodApi.fetchContents()
}