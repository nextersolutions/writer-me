package io.writerme.resources.helpers

import io.writerme.resources.helpers.MultipleEventsCutter.Companion.DEFAULT_DELAY

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object {
        const val DEFAULT_DELAY = 1000
    }
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= DEFAULT_DELAY) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}
