package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY

data class HistoryViewData(
    val id: String = EMPTY,
    val changes: List<ComponentViewData> = emptyList()
)
