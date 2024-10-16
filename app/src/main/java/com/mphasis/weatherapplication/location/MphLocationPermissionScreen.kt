package com.mphasis.weatherapplication.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.weatherapplication.ui.component.Home

/**
 * A composable function to request permission.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    userLocationSuccess: () -> Unit,
    cityWeatherState: State<MphWeatherDataByCity>
) {

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        //All Permission is good so load the home screen
        //and run location permission success callback.
        userLocationSuccess()
        Home(cityWeatherState)
    } else {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceBright,
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                val allPermissionsRevoked =
                    locationPermissionsState.permissions.size ==
                            locationPermissionsState.revokedPermissions.size

                val textToShow = if (!allPermissionsRevoked) {
                    "We are accessing your approximate location now."
                } else if (locationPermissionsState.shouldShowRationale) {
                    "We really need location to continue."
                } else {
                    "This part of the app needs your location permission"
                }

                val buttonText = if (!allPermissionsRevoked) {
                    "Allow precise location"
                } else {
                    "Request permissions"
                }

                Text(text = textToShow, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(18.dp))
                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text(buttonText)
                }
            }
        }
    }
}

