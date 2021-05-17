package com.example.ganesh.myktxlibrary

/**
 * Created by Ganesh Padhayay on 17/05/21.
 */
import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun createLocationRequest() = LocationRequest.create().apply {
    interval = 3000
    fastestInterval = 2000
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
}

fun Location.asString(format: Int = Location.FORMAT_DEGREES): String {
    val latitude = Location.convert(latitude, format)
    val longitude = Location.convert(longitude, format)
    return "Location is: $latitude, $longitude"
}

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): Location =
        suspendCancellableCoroutine<Location> { continuation ->
            lastLocation.addOnSuccessListener { location ->
                continuation.resume(location)
            }.addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
        }

@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.locationFlow() = callbackFlow<Location> {
    val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result ?: return
            for (location in result.locations) {
                try {
                    offer(location) // emit location into the Flow using ProducerScope.offer
                } catch (e: Exception) {
                    // nothing to do
                    // Channel was probably already closed by the time offer was called
                }
            }
        }
    }

    requestLocationUpdates(
            createLocationRequest(),
            callback,
            Looper.getMainLooper()
    ).addOnFailureListener { e ->
        close(e) // in case of exception, close the Flow
    }

    awaitClose {
        removeLocationUpdates(callback) // clean up when Flow collection ends
    }
}
