package com.mphasis.domain.usecase

import com.mphasis.data.model.WeatherData
import com.mphasis.data.repository.MphWeatherByCityRepository
import com.mphasis.domain.model.MphCommonData
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.domain.model.error.MphErrorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This file will contain all the use case implementations
 * need for the application.
 *
 * @author Kal Tadesse
 */

/**
 * Define and override invoke() functional operator.
 * to define a block that takes city name.
 */
interface MphWeatherByCityUseCase {

    /**
     * Invoke the use case by city name.
     *
     * @param cityName
     * @return [MphCommonData] as a flow.
     */
    suspend operator fun invoke(cityName: String): Flow<MphCommonData>

}

/**
 * A weather by city use case to be used in conjunction with
 * the respective viewmodel - supporting the domain layer in the line
 * of MVVM and clean architecture.
 */
class MphWeatherByCityUseCaseImpl @Inject constructor(private val repository: MphWeatherByCityRepository) :
    MphBaseUseCaseImpl<WeatherData, MphWeatherDataByCity, MphErrorData>(), MphWeatherByCityUseCase {

    override suspend fun invoke(cityName: String): Flow<MphCommonData> =
        repository.getWeatherData(cityName).map {
            it.transform()
        }

    override fun processFailure(): MphErrorData {
        return MphErrorData("We are currently experiencing some difficulties - please try again later.")
    }

    override fun processValue(value: WeatherData): MphWeatherDataByCity {
        return MphWeatherDataByCity().also {
            it.name = value.name
            it.weatherType = value.weather[0].main
            it.weatherDescription = value.weather[0].description
            it.humidity = value.main.humidity
            it.pressure = value.main.pressure
            it.feelsLike = value.main.feelsLike
            it.temprature = value.main.temp
            it.visibility = value.visibility
            it.icon = value.weather[0].icon
        }
    }

}
