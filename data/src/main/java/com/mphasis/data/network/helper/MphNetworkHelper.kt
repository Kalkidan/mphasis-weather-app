package com.mphasis.data.network.helper

import com.mphasis.data.dispatcher.di.Dispatcher
import com.mphasis.data.dispatcher.di.MphDispatchers
import com.mphasis.data.model.WeatherData
import com.mphasis.data.network.MphRetrofitApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

/**
 * This file will establish a supplementary role
 * in network functionality
 *
 * @author Kal Tadesse
 */

/*************************************************************
 * Step 1 - Define Distinct and Strict Network Result States
 *************************************************************/
sealed interface MphNetworkResponse {
    data class Success<T>(val data: T) : MphNetworkResponse
    data class Error(val message: String) : MphNetworkResponse
    data object Unknown : MphNetworkResponse
}

/**********************************************************************
 * Step 2 - Define toResult() parsing functionality -
 * Note Here - this is preferential and open ended as far as
 * developers are concerned - however, keeping it concise and
 * targeted by using extension functions would be most liked, hence,
 * the approach in this project.
 ********************************************************************/
fun <T> Response<T>.toResult(): MphNetworkResponse = when {
    isSuccessful && body() != null -> MphNetworkResponse.Success(body())
    !isSuccessful || body() == null -> MphNetworkResponse.Error("")
    else -> MphNetworkResponse.Unknown
}

/*********************************************************************
 * Step 3 - Construct a convenience wrapper class along with its
 * respective API definition to be implemented and used
 * around retrofit class.
 *********************************************************************/
class MphNetworkDataSourceImpl @Inject constructor(
    val network: MphRetrofitApi,
    @Dispatcher(MphDispatchers.IO) val ioDispatcher: CoroutineDispatcher
) : MphNetworkDataSource {

    override suspend fun getWeatherBy(cityName: String): Flow<Response<WeatherData>> = flow {
        emit(network.getWeatherBy(cityName))
    }.flowOn(ioDispatcher)

}

interface MphNetworkDataSource {
    suspend fun getWeatherBy(cityName: String): Flow<Response<WeatherData>>
}

/********************************
 *             END              *
 ********************************/