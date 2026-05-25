package io.writerme.app.ui.state

import io.writerme.app.data.viewdata.ComponentViewData

data class AudioState(
    val audio: ComponentViewData,
    val currentProgress: Long,
    val entireLength: Long
)
