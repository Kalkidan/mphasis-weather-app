package com.mphasis.weatherapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.domain.viewmodel.MphWeatherByCityViewModel
import com.mphasis.weatherapplication.location.RequestPermission
import com.mphasis.weatherapplication.ui.component.Home
import com.mphasis.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This will be used as Mphasis weather landing activity
 * that will be hosting all the composable screens as a root.
 *
 * @author Kal Tadesse
 */

@AndroidEntryPoint
class MphWeatherLandingActivity : ComponentActivity() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val viewModel: MphWeatherByCityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApplicationTheme {
                /************************************************************************************
                 * This is the driving code for the application - the idea is:
                 * 1 - Check permission using composable function [RequestPermission]
                 * 2 - Based on that response - process the location data:
                 * 3 - This data is from either the good last known location or current one.
                 * 4 - If [getLastGoodKnownUserLocation] returns success - then process it through
                 * the [MphWeatherByCityViewModel] to get the data for the city.
                 * 5 - If failure - for now its not done - because of time constraints
                 * 6 - If location is returned NULL then go for current location and do the same -
                 * meaning, if success process the request through the viewmodel
                 * *************************************************************************************/
                RequestPermission(
                    userLocationPermissionSuccess = {
                        getLastGoodKnownUserLocation(
                            userLocationSuccess = { locationData ->
                                viewModel.weatherByCity(
                                    locationData.first,
                                    locationData.second
                                )
                            },
                            userLocationFailure = {
                                //Do nothing for now
                            },
                            userLocationNull = {
                                //Here let us try to get the user's current location
                                getCurrentLocation(
                                    currentLocationSuccess = { locationData ->
                                        viewModel.weatherByCity(
                                            locationData.first,
                                            locationData.second
                                        )
                                    },
                                    currentLocationFailure = {
                                        //Do nothing for now
                                    }
                                )
                            }
                        )
                    },
                    //Don't pass view model instance
                    //either use state hoisting or
                    //use navigation hiltViewModel<>() for injecting
                    //view models scoped for composable functions
                    //for our purposes - state hoisting suffices.
                    viewModel.cityWeatherState,
                    viewModel.cityWeatherErrorState,
                    viewModel.onSearchQuery,
                    viewModel.onSearchFail
                )
            }
        }
    }

    /**
     * Gets the last user known location.
     */
    private fun getLastGoodKnownUserLocation(
        userLocationSuccess: (Pair<Double, Double>) -> Unit = { },
        userLocationFailure: (Exception) -> Unit = { },
        userLocationNull: () -> Unit = { },
    ) {
        if ((ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
        ) {
            //Use this to know the last known -
            // meaning, good known user location.
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { locationData ->
                    locationData?.let {
                        userLocationSuccess(Pair(it.latitude, it.longitude))
                    }
                }.addOnFailureListener { failureException ->
                    userLocationFailure(failureException)
                }
        }
    }

    /**
     * Gets the user current location
     */
    private fun getCurrentLocation(
        currentLocationSuccess: (Pair<Double, Double>) -> Unit,
        currentLocationFailure: (Exception) -> Unit,
        currentLocationPriorityFlag: Boolean = true
    ) {
        //Use advanced setting whilst it is available
        val locationAlgorithmAccuracy =
            if (currentLocationPriorityFlag) Priority.PRIORITY_HIGH_ACCURACY
            else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        if ((ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
        ) {
            fusedLocationProviderClient.getCurrentLocation(
                locationAlgorithmAccuracy, CancellationTokenSource().token,
            ).addOnSuccessListener { locationData ->
                locationData?.let {
                    currentLocationSuccess(Pair(it.latitude, it.longitude))
                }
            }.addOnFailureListener { exception ->
                currentLocationFailure(exception)
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    WeatherApplicationTheme {
        Home(mutableStateOf(MphWeatherDataByCity())) { }
    }
}

