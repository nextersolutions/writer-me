package io.writerme.core.models.event

sealed class AnalyticsEvent(val key: String) {
    data object UserLogin : AnalyticsEvent("userLogin")
    data class ClientSelectLocation(
        val locationName: String
    ) : AnalyticsEvent("clientSelectLocation")
}
