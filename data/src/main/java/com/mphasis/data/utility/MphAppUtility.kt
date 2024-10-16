package com.mphasis.data.utility

import android.location.Address
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * This method will help us in getting the current address
 * from latitude and longitude
 *
 * @param lat - latitude
 * @param long - longitude
 */
suspend fun Geocoder.getAddress(
    lat: Double,
    long: Double,
): Address? = withContext(Dispatchers.IO) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine { cont ->
                getFromLocation(lat, long, 1) {
                    cont.resume(it.firstOrNull())
                }
            }
        } else {
            suspendCoroutine { cont ->
                @Suppress("DEPRECATION")
                val address = getFromLocation(lat, long, 1)?.firstOrNull()
                cont.resume(address)
            }
        }
    } catch (e: Exception) {
        null
    }
}