package com.shov.unlimstorage.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
	val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	connectivityManager.registerDefaultNetworkCallback(ConnectivityManager.NetworkCallback())

	val callback = networkCallback { connectionState -> trySend(connectionState) }

	val networkRequest = NetworkRequest.Builder()
		.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
		.build()

	connectivityManager.registerNetworkCallback(networkRequest, callback)

	trySend(
		connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
			?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
	)

	awaitClose {
		connectivityManager.unregisterNetworkCallback(callback)
	}
}

private fun networkCallback(callback: (Boolean) -> Unit): ConnectivityManager.NetworkCallback {
	return object : ConnectivityManager.NetworkCallback() {
		override fun onAvailable(network: Network) {
			callback(true)
		}

		override fun onLost(network: Network) {
			callback(false)
		}
	}
}
