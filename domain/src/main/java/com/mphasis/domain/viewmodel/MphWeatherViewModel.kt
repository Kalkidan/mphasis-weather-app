package com.mphasis.domain.viewmodel

import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mphasis.data.utility.getAddress
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.domain.model.error.MphErrorData
import com.mphasis.domain.usecase.MphWeatherByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This file will contain view model implementation for
 * the app as a state holder that outlives the UI component
 * created it.
 *
 * @author Kal Tadesse
 */

/**
 * A view model for supporting states needed for
 * by city implementation from search
 */
@HiltViewModel
class MphWeatherByCityViewModel @Inject constructor(
    private val useCase: MphWeatherByCityUseCase,
    private val geocoder: Geocoder,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //Success State
    private val cityWeatherMutableState = mutableStateOf(MphWeatherDataByCity())
    val cityWeatherState: State<MphWeatherDataByCity> = cityWeatherMutableState

    //Error State
    private val cityWeatherMutableErrorState = mutableStateOf(MphErrorData(""))
    val cityWeatherErrorState: State<MphErrorData> = cityWeatherMutableErrorState

    //A convenient method for triggering search query.
    val onSearchQuery: (String) -> Unit = { value -> weatherByCity(value) }

    //A convenient method for reloading the landing page when search fails.
    val onSearchFail: () -> Unit = {
        //Reset this - in order the composable avoid showing
        //alert dialog again since State<T> still holds old value
        //reset - key approach in observable patterns.
        cityWeatherMutableErrorState.value = MphErrorData("")
        useCoordinatesToSearch()
    }

    //saving the search query, lat, and long for future use
    private val latitude = savedStateHandle.get<Double>("latitude") ?: 0.0
    private val longitude = savedStateHandle.get<Double>("longitude") ?: 0.0
    private val searchQuery = savedStateHandle.get<String>("searchQuery") ?: ""

    /**
     * This is a fall back functionality - inorder for us
     * to navigate to landing page when search fails - due to many reasons
     * for example, the user may type a misspelled city.
     */
    private fun useCoordinatesToSearch() {
        weatherByCity(latitude, longitude)
    }


    /**
     * This function will attempt to get the weather by
     * city name.
     * @param cityName
     */
    private fun weatherByCity(cityName: String) {
        savedStateHandle["searchQuery"] = cityName
        viewModelScope.launch {
            useCase.invoke(cityName).collect {
                when (it) {
                    is MphWeatherDataByCity -> cityWeatherMutableState.value = it
                    is MphErrorData -> cityWeatherMutableErrorState.value = it
                }
            }
        }
    }

    /**
     * This function will attempt to get weather by using
     * latitude and longitude.
     *
     * @param latitude
     * @param longitude
     */
    fun weatherByCity(latitude: Double, longitude: Double) {
        if(searchQuery.isNotEmpty()) {
            //This means we already have search results from previous launch
            //so launch that result.
            weatherByCity(searchQuery)
            return
        }
        //Otherwise get the lat and long and
        //populate the city's weather.
        savedStateHandle["latitude"] = latitude
        savedStateHandle["longitude"] = longitude
        viewModelScope.launch {
            val cityName = geocoder.getAddress(latitude, longitude)?.locality ?: "New York"
            useCase.invoke(cityName).collect {
                when (it) {
                    is MphWeatherDataByCity -> cityWeatherMutableState.value = it
                    is MphErrorData -> cityWeatherMutableErrorState.value = it
                }
            }
        }
    }
}