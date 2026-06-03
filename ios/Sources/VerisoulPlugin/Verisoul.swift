import Foundation
import VerisoulSDK

@objc public class VerisoulBridge: NSObject {
    private let environments: [String: VerisoulEnvironment] = [
        "dev": .dev,
        "prod": .prod,
        "production": .prod,
        "sandbox": .sandbox,
        "staging": .staging
    ]

    @objc public func configure(environment: String, projectId: String) throws {
        guard let env = environments[environment] else {
            throw VerisoulBridgeError.invalidEnvironment(environment)
        }

        try VerisoulSDK.Verisoul.shared.configure(env: env, projectId: projectId)
    }

    @objc public func getSessionId() async throws -> String {
        return try await VerisoulSDK.Verisoul.shared.session()
    }

    @objc public func reinitialize() {
        VerisoulSDK.Verisoul.shared.reinitialize()
    }
}

enum VerisoulBridgeError: LocalizedError {
    case invalidEnvironment(String)

    var errorDescription: String? {
        switch self {
        case .invalidEnvironment(let environment):
            return "Invalid environment value: \(environment)"
        }
    }
}
