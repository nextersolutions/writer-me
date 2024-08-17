package io.writerme.resources.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import io.writerme.core.common.FormatUtils
import io.writerme.core.contracts.handlers.SmsCodeHandler
class SmsCodeReceiver(
    private val handler: SmsCodeHandler
) : BroadcastReceiver(), LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                try {
                    val status =
                        bundle.getParcelable(SmsRetriever.EXTRA_STATUS) as? Status ?: return
                    when (status.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            val message =
                                intent.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE) ?: return
                            handler.onReceived(FormatUtils.clearOtpFormat(message))
                        }

                        CommonStatusCodes.TIMEOUT -> {
                            handler.timeout()
                        }

                        else -> {
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}
