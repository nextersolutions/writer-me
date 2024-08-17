package io.writerme.core.contracts.dispatchers

import io.writerme.core.models.dto.base.OperationResult

enum class ConnectionState {
    WIFI, MOBILE, NO_CONNECTION, ETHERNET, VPN
}

interface IConnectionDetector {

    suspend fun isConnectingToInternet(withPingCheck: Boolean = false): Boolean
    fun getConnectionState(): ConnectionState
    suspend fun <T> validateConnection(
        withPingCheck: Boolean = false,
        function: suspend () -> OperationResult<T>
    ): OperationResult<T>
    suspend fun validateConnectionUnit(function: suspend () -> Unit): OperationResult<String?>
}
