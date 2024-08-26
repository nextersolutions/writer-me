package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY

data class HistoryViewData(
    val id: String = EMPTY,
    val changes: List<ComponentViewData> = emptyList()
) {
    fun newest(): ComponentViewData? {
        return changes.firstOrNull()
    }

    fun isNotEmpty(): Boolean {
        return changes.isNotEmpty()
    }
}
