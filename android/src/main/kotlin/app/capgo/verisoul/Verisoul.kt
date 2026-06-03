package app.capgo.verisoul

import ai.verisoul.sdk.InternalVerisoulCore
import ai.verisoul.sdk.SDKType
import ai.verisoul.sdk.Verisoul
import ai.verisoul.sdk.VerisoulEnvironment
import ai.verisoul.sdk.helpers.webview.VerisoulSessionCallback
import android.content.Context
import android.view.MotionEvent

class VerisoulBridge(private val context: Context) {

    private val environments = mapOf(
        "dev" to VerisoulEnvironment.Dev,
        "prod" to VerisoulEnvironment.Prod,
        "production" to VerisoulEnvironment.Prod,
        "sandbox" to VerisoulEnvironment.Sandbox,
        "staging" to VerisoulEnvironment.Sandbox,
    )

    fun configure(environment: String, projectId: String) {
        val env = environments[environment] ?: throw IllegalArgumentException("Invalid environment value: $environment")

        InternalVerisoulCore.sdkType = SDKType.ReactNative
        Verisoul.init(context, env, projectId)
    }

    fun getSessionId(callback: VerisoulSessionCallback) {
        Verisoul.getSessionId(callback)
    }

    fun reinitialize() {
        Verisoul.reinitialize()
    }

    fun recordTouchEvent(x: Float, y: Float, action: Int) {
        val now = System.currentTimeMillis()
        val event = MotionEvent.obtain(now, now, action, x, y, 0)
        try {
            Verisoul.onTouchEvent(event)
        } finally {
            event.recycle()
        }
    }
}
