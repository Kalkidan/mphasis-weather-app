package com.mphasis.weatherapplication.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.mphasis.domain.model.MphWeatherDataByCity

/**
 * A composable function that will serve us
 * as a Home destination
 */
@Composable
fun Home(cityWeatherState: State<MphWeatherDataByCity>, onSearchQuery: (String) -> Unit) {
    Column {
        SearchTextField(onSearchQuery)
        DetailWeatherScreen(cityWeatherState)
    }
}