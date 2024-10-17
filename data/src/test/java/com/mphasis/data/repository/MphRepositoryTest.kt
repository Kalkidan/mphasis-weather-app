package com.mphasis.data.repository

import com.mphasis.data.dispatcher.di.Dispatcher
import com.mphasis.data.dispatcher.di.MphDispatchers
import com.mphasis.data.model.WeatherData
import com.mphasis.data.network.MphRetrofitApi
import com.mphasis.data.network.helper.MphNetworkDataSource
import com.mphasis.data.network.helper.MphNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * A repository test.
 *
 * @author Kal Tadesse
 */
class MphRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRepositoryMethodForLoadingCityWeather() = runTest {
        val fakeDataSource = MphTestNetworkDataSource(
            network = MphTestRetrofitApi(),
            ioDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        val repository = MphWeatherByCityRepositoryImpl(networkDataSource = fakeDataSource)
        repository.getWeatherData("New York").map {
            it as MphNetworkResponse.Success<*>
        }.collect {
            assertEquals((it.data as WeatherData).name, "New York")
        }
    }
}

class MphTestNetworkDataSource(
    val network: MphRetrofitApi,
    @Dispatcher(MphDispatchers.IO) val ioDispatcher: CoroutineDispatcher
) : MphNetworkDataSource {

    override suspend fun getWeatherBy(cityName: String): Flow<Response<WeatherData>> =
        flow {
            emit(network.getWeatherBy(cityName))
        }.flowOn(ioDispatcher)

}

class MphTestRetrofitApi : MphRetrofitApi {
    override suspend fun getWeatherBy(cityName: String, appId: String): Response<WeatherData> =
        Response.success(
            WeatherData(name = cityName)
        )

}