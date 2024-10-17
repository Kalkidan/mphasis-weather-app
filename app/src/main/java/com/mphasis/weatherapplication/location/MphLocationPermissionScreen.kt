package com.mphasis.weatherapplication.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.domain.model.error.MphErrorData
import com.mphasis.weatherapplication.ui.component.Home

/**
 * A composable function to request permission.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    userLocationPermissionSuccess: () -> Unit,
    cityWeatherState: State<MphWeatherDataByCity>,
    cityWeatherErrorState: State<MphErrorData>,
    onSearchQuery: (String) -> Unit,
    onSearchFail: () -> Unit
) {

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    //If the network returns error - then show the alert dialog
    //We are auto loading using users current location when launching
    //the application - so if there is any error - we will be
    //showing a dialog.
    if (cityWeatherErrorState.value.message.isNotEmpty()) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(0.92f),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            shape = RoundedCornerShape(20.dp),
            onDismissRequest = { },
            confirmButton = {
                //If we are here after search fails - that means
                //we have location permission - check it and try to load
                //with current coordinates
                TextButton(onClick = {
                    if (locationPermissionsState.allPermissionsGranted) onSearchFail() }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                //If we are here after search fails - that means
                //we have location permission - check it and try to load
                //with current coordinates
                TextButton(onClick = { if (locationPermissionsState.allPermissionsGranted) onSearchFail() }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Something Went Wrong", fontSize = 18.sp)
            },
            text = {
                Text(
                    text = "If you are here from search - " +
                            "please make sure that you put in" +
                            " the correct spelling. Our search needs to match the place you are " +
                            "intending letter for letter."
                )
            })
    } else {


        if (locationPermissionsState.allPermissionsGranted) {
            //All Permission is good so load the home screen
            //and run location permission success callback.
            userLocationPermissionSuccess()
            Home(cityWeatherState, onSearchQuery)
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
}

