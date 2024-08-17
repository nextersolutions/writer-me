package io.writerme.core.contracts.usecases.event

import io.writerme.core.models.event.AnalyticsEvent

interface IAnalyticsEventUseCase {
    suspend fun sendEvent(event: AnalyticsEvent)
}
