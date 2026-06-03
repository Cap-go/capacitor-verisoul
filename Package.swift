// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorVerisoul",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapgoCapacitorVerisoul",
            targets: ["VerisoulPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/verisoul/ios-sdk.git", from: "0.4.66")
    ],
    targets: [
        .target(
            name: "VerisoulPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "VerisoulSDK", package: "ios-sdk")
            ],
            path: "ios/Sources/VerisoulPlugin"),
        .testTarget(
            name: "VerisoulPluginTests",
            dependencies: ["VerisoulPlugin"],
            path: "ios/Tests/VerisoulPluginTests")
    ]
)
