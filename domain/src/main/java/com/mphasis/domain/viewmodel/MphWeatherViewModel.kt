package com.mphasis.domain.viewmodel

import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mphasis.data.utility.getAddress
import com.mphasis.domain.model.MphWeatherDataByCity
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
    private val geocoder: Geocoder
) :
    ViewModel() {

    private val cityWeatherMutableState = mutableStateOf(MphWeatherDataByCity())
    val cityWeatherState: State<MphWeatherDataByCity> = cityWeatherMutableState

    fun weatherByCity(cityName: String) {
        viewModelScope.launch {
            useCase.invoke(cityName).collect {
                when (it) {
                    is MphWeatherDataByCity -> cityWeatherMutableState.value = it
                }
            }
        }
    }

    fun weatherByCity(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val defaultAddress = Address(java.util.Locale("en")).apply { this.locality = "London" }
            val currentAddress = geocoder.getAddress(latitude, longitude) ?: defaultAddress
            val cityName = currentAddress.locality
            useCase.invoke(cityName).collect {
                when (it) {
                    is MphWeatherDataByCity -> cityWeatherMutableState.value = it
                }
            }
        }
    }

}