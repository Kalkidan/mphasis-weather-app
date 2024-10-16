package com.mphasis.data.network

import com.mphasis.data.BuildConfig
import com.mphasis.data.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit api definition for network call
 * within the application.
 *
 * @author Kal Tadesse
 */
interface MphRetrofitApi {

    /**
     * A GET weather end point to collect weather data
     * from the back end.
     *
     * @return [WeatherData]
     */
    @GET(value = BuildConfig.WEATHER_PATH)
    suspend fun getWeatherBy(
        @Query("q") cityName: String,
        @Query("appId") appId: String = BuildConfig.API_KEY
    ): Response<WeatherData>

    /**
     * A GET weather end point to collect weather data
     * from the back end.
     *
     * @return [WeatherData]
     */
    @GET(value = BuildConfig.WEATHER_PATH)
    suspend fun getWeatherBy(
        @Query("lat") lat: Double,
        @Query("long") long: Double,
        @Query("appId") appId: String = BuildConfig.API_KEY
    ): Response<WeatherData>
}