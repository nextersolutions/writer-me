package io.writerme.core.models.network

sealed class NetworkStatus {
    data object Unknown : NetworkStatus()
    data object Connected : NetworkStatus()
    data object Disconnected : NetworkStatus()
}
