package sk.figlar.apodjc

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import sk.figlar.apodjc.data.ApodApi
import sk.figlar.apodjc.model.ApodApiModel
import timber.log.Timber

class ApodRepository {
    private val apodApi: ApodApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        apodApi = retrofit.create()
    }

    suspend fun getApodApiModels(): Result<List<ApodApiModel>, ApiError> {
        try {
            val list = apodApi.getApodApiModels("2024-10-15")
            return Result.Success(list)
        } catch (ex: Exception) {
            Timber.e("Failed to fetch gallery items: $ex")
            return Result.Error(ApiError.UNKNOWN)
        }
    }

    enum class ApiError: Error {
        UNKNOWN,
    }
}