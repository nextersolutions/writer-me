package io.writerme.core.contracts.handlers

interface SmsCodeHandler {
    fun onReceived(value: String)
    fun timeout()
}
