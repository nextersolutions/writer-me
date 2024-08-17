package io.writerme.core.network

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import io.writerme.core.common.ApiRoutes.ResponseMessages.SUCCESS
import io.writerme.core.common.GlobalConstants.Ping.host
import io.writerme.core.common.GlobalConstants.Ping.port
import io.writerme.core.common.GlobalConstants.Ping.responseTime
import io.writerme.core.contracts.dispatchers.ConnectionState
import io.writerme.core.contracts.dispatchers.IConnectionDetector
import io.writerme.core.contracts.dispatchers.ICoroutineDispatcher
import io.writerme.core.models.dto.base.OperationResult
import io.writerme.core.models.dto.base.OperationResultType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConnectionDetector @Inject constructor(
    private val application: Application,
    private val coroutineDispatcher: ICoroutineDispatcher
) :
    IConnectionDetector {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun isConnectingToInternet(withPingCheck: Boolean): Boolean {
        val connectivity =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false
        val network: Network = connectivity.activeNetwork ?: return false
        val networkCapabilities: NetworkCapabilities =
            connectivity.getNetworkCapabilities(network) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                if (withPingCheck) isConnected() else true

            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                if (withPingCheck) isConnected() else true

            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                if (withPingCheck) isConnected() else true

            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ->
                if (withPingCheck) isConnected() else true

            else -> false
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun getConnectionState(): ConnectionState {
        val connectivity =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return ConnectionState.NO_CONNECTION

        val network: Network =
            connectivity.activeNetwork ?: return ConnectionState.NO_CONNECTION
        val networkCapabilities: NetworkCapabilities =
            connectivity.getNetworkCapabilities(network) ?: return ConnectionState.NO_CONNECTION

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionState.WIFI
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionState.MOBILE
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionState.ETHERNET
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> ConnectionState.VPN
            else -> ConnectionState.NO_CONNECTION
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun <T> validateConnection(
        withPingCheck: Boolean,
        function: suspend () -> OperationResult<T>
    ): OperationResult<T> {
        return if (isConnectingToInternet()) {
            withContext(coroutineDispatcher.io) { function.invoke() }
        } else {
            OperationResult(type = OperationResultType.NetworkConnectionLost)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun validateConnectionUnit(function: suspend () -> Unit): OperationResult<String?> {
        return if (isConnectingToInternet()) {
            withContext(coroutineDispatcher.io) {
                function.invoke()
                OperationResult(message = SUCCESS, value = null)
            }
        } else {
            OperationResult(type = OperationResultType.NetworkConnectionLost)
        }
    }

    private suspend fun isConnected(): Boolean = withContext(coroutineDispatcher.io) {
        coroutineScope {
            suspendCoroutine { continuation ->
                runCatching {
                    val sock = Socket()
                    val sockaddr: SocketAddress = InetSocketAddress(host, port)
                    sock.connect(sockaddr, responseTime)
                    sock.close()
                    continuation.resume(true)
                }.getOrElse {
                    continuation.resume(false)
                }
            }
        }
    }
}
