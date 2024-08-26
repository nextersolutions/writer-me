package io.writerme.core.models.viewdata

data class AudioStateViewData(
    val audio: ComponentViewData,
    val currentProgress: Long,
    val entireLength: Long
)
