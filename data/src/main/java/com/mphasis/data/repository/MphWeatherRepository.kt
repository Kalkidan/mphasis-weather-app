package com.mphasis.data.repository

import com.mphasis.data.model.WeatherData
import com.mphasis.data.network.helper.MphNetworkDataSource
import com.mphasis.data.network.helper.MphNetworkResponse
import com.mphasis.data.network.helper.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This file will be used as a weather repository to implement
 * network data functionality.
 *
 * @author Kal Tadesse
 */

/**
 * API contract for implementing weather data functionality
 * by city.
 */
interface MphWeatherByCityRepository {

    /**
     * Gets a weather data from network using:
     *
     * @param cityName query value for city.
     * @return [MphCommonData] as a  flow.
     */
    suspend fun getWeatherData(cityName: String): Flow<MphNetworkResponse>

}

/**
 * API implementation for getting the weather data from
 * network. This will be used to get the weather by using
 * city name.
 */
class MphWeatherByCityRepositoryImpl @Inject constructor(private val networkDataSource: MphNetworkDataSource) :
    MphBaseRepositoryImpl<WeatherData>(), MphWeatherByCityRepository {

    override suspend fun getWeatherData(cityName: String): Flow<MphNetworkResponse> =
        networkDataSource.getWeatherBy(cityName).map {
            it.toResult()
        }

}