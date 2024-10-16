package com.mphasis.domain.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
 * by city implementation
 */
@HiltViewModel
class MphWeatherByCityViewModel @Inject constructor(private val useCase: MphWeatherByCityUseCase) :
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

}