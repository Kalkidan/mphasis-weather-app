package com.mphasis.domain.usecase

import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.data.model.WeatherData
import com.mphasis.domain.model.error.MphErrorData
import com.mphasis.data.repository.MphCommonData
import com.mphasis.data.repository.MphWeatherByCityRepository
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
        return MphErrorData("")
    }

    override fun processValue(value: WeatherData): MphWeatherDataByCity {
        return MphWeatherDataByCity()
    }


}

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