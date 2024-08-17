package io.writerme.core.contracts.validators

import io.writerme.core.models.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface INetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}
