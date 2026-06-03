import Foundation
import Capacitor

@objc(VerisoulPlugin)
public class VerisoulPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "VerisoulPlugin"
    public let jsName = "Verisoul"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSessionId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reinitialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "recordTouchEvent", returnType: CAPPluginReturnPromise)
    ]

    private let implementation = VerisoulBridge()

    @objc func configure(_ call: CAPPluginCall) {
        guard let environment = call.getString("environment") else {
            call.reject("environment is required", "INVALID_ENVIRONMENT")
            return
        }
        guard let projectId = call.getString("projectId"), !projectId.isEmpty else {
            call.reject("projectId is required")
            return
        }

        do {
            try implementation.configure(environment: environment, projectId: projectId)
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, errorCode(for: error), error)
        }
    }

    @objc func getSessionId(_ call: CAPPluginCall) {
        Task {
            do {
                let sessionId = try await implementation.getSessionId()
                call.resolve(["sessionId": sessionId])
            } catch {
                call.reject(error.localizedDescription, errorCode(for: error), error)
            }
        }
    }

    @objc func reinitialize(_ call: CAPPluginCall) {
        implementation.reinitialize()
        call.resolve()
    }

    @objc func recordTouchEvent(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func errorCode(for error: Error) -> String {
        let description = error.localizedDescription.uppercased()
        if description.contains("ENVIRONMENT") {
            return "INVALID_ENVIRONMENT"
        }
        if description.contains("WEBVIEW") {
            return "WEBVIEW_UNAVAILABLE"
        }
        if description.contains("SESSION") {
            return "SESSION_UNAVAILABLE"
        }
        return "UNKNOWN_ERROR"
    }
}
