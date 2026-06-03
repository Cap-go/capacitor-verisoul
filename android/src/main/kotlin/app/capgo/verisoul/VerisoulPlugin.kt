package app.capgo.verisoul

import ai.verisoul.sdk.VerisoulException
import ai.verisoul.sdk.helpers.webview.VerisoulSessionCallback
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import android.view.MotionEvent

@CapacitorPlugin(name = "Verisoul")
class VerisoulPlugin : Plugin() {

    private val implementation by lazy { VerisoulBridge(context) }

    @PluginMethod
    fun configure(call: PluginCall) {
        val environment = call.getString("environment")
        val projectId = call.getString("projectId")

        if (environment.isNullOrBlank()) {
            call.reject("environment is required", VerisoulErrorCodes.INVALID_ENVIRONMENT)
            return
        }
        if (projectId.isNullOrBlank()) {
            call.reject("projectId is required")
            return
        }

        try {
            implementation.configure(environment, projectId)
            call.resolve()
        } catch (exception: Throwable) {
            rejectVerisoul(call, "SDK configuration failed", exception)
        }
    }

    @PluginMethod
    fun getSessionId(call: PluginCall) {
        try {
            implementation.getSessionId(object : VerisoulSessionCallback {
                override fun onFailure(exception: Throwable) {
                    rejectVerisoul(call, "Failed to retrieve session ID", exception)
                }

                override fun onSuccess(sessionId: String) {
                    val ret = JSObject().apply {
                        put("sessionId", sessionId)
                    }
                    call.resolve(ret)
                }
            })
        } catch (exception: Throwable) {
            rejectVerisoul(call, "Failed to retrieve session ID", exception)
        }
    }

    @PluginMethod
    fun reinitialize(call: PluginCall) {
        try {
            implementation.reinitialize()
            call.resolve()
        } catch (exception: Throwable) {
            rejectVerisoul(call, "Failed to reinitialize SDK", exception)
        }
    }

    @PluginMethod
    fun recordTouchEvent(call: PluginCall) {
        val x = call.getFloat("x")
        val y = call.getFloat("y")
        val action = parseAction(call.getString("action"), call.getInt("action"))

        if (x == null || y == null || action == null) {
            call.reject("x, y, and action are required")
            return
        }

        try {
            implementation.recordTouchEvent(x, y, action)
            call.resolve()
        } catch (exception: Throwable) {
            rejectVerisoul(call, "Failed to record touch event", exception)
        }
    }

    private fun parseAction(actionString: String?, actionInt: Int?): Int? {
        return when (actionString) {
            "down" -> MotionEvent.ACTION_DOWN
            "up" -> MotionEvent.ACTION_UP
            "move" -> MotionEvent.ACTION_MOVE
            else -> when (actionInt) {
                0 -> MotionEvent.ACTION_DOWN
                1 -> MotionEvent.ACTION_UP
                2 -> MotionEvent.ACTION_MOVE
                else -> null
            }
        }
    }

    private fun rejectVerisoul(call: PluginCall, prefix: String, exception: Throwable) {
        val code = (exception as? VerisoulException)?.code ?: errorCodeFromMessage(exception)
        call.reject("$prefix: ${exception.message}", code, exception)
    }

    private fun errorCodeFromMessage(exception: Throwable): String {
        val message = exception.message.orEmpty().uppercase()
        return when {
            message.contains("ENVIRONMENT") -> VerisoulErrorCodes.INVALID_ENVIRONMENT
            message.contains("WEBVIEW") -> VerisoulErrorCodes.WEBVIEW_UNAVAILABLE
            message.contains("SESSION") -> VerisoulErrorCodes.SESSION_UNAVAILABLE
            else -> "UNKNOWN_ERROR"
        }
    }
}
