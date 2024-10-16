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
                RequestPermission(
                    userLocationSuccess = {
                        getLastGoodKnownUserLocation(
                            userLocationSuccess = { locationData ->
                                viewModel.weatherByCity(
                                    locationData.first,
                                    locationData.second
                                )
                            },
                            userLocationFailure = {

                            },
                            userLocationNull = {

                            }
                        )
                    },
                    viewModel.cityWeatherState
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
fun GreetingPreview() {
    WeatherApplicationTheme {
        Home(mutableStateOf(MphWeatherDataByCity()))
    }
}

