package com.mphasis.domain.usecase

import com.mphasis.data.model.Weather
import com.mphasis.data.model.WeatherData
import com.mphasis.data.network.helper.MphNetworkResponse
import com.mphasis.data.repository.MphBaseRepositoryImpl
import com.mphasis.data.repository.MphWeatherByCityRepository
import com.mphasis.domain.model.MphWeatherDataByCity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * A use case test.
 *
 * @author Kal Tadesse
 */
class MphUseCaseTest {

    private val fakeRepository = MphTestWeatherDataRepository()

    private val subject = MphWeatherByCityUseCaseImpl(fakeRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUseCaseInvokableMethod() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            subject.invoke("London").map {
                it as MphWeatherDataByCity
            }.collect {
                assertEquals(it.name, "London")
            }
        }
    }

}

class MphTestWeatherDataRepository : MphBaseRepositoryImpl<WeatherData>(),
    MphWeatherByCityRepository {

    override suspend fun getWeatherData(cityName: String): Flow<MphNetworkResponse> = flow {
        emit(MphNetworkResponse.Success(WeatherData(name = cityName, weather = listOf(Weather()))))
    }
}