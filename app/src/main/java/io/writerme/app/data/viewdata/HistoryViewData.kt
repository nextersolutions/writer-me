package io.writerme.app.data.viewdata

import io.writerme.app.data.model.ComponentType

data class HistoryViewData(
    val id: Long,
    val changes: List<ComponentViewData>
) {
    fun newest(): ComponentViewData? = changes.lastOrNull()
    fun getType(): ComponentType? = changes.firstOrNull()?.type
    fun isNotEmpty(): Boolean = changes.isNotEmpty()

    companion object {
        fun empty(): HistoryViewData = HistoryViewData(System.currentTimeMillis(), emptyList())
    }
}
