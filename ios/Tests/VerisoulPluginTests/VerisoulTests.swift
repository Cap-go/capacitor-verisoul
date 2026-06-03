import XCTest
@testable import VerisoulPlugin

class VerisoulTests: XCTestCase {
    func testInvalidEnvironmentThrows() {
        let implementation = VerisoulBridge()

        XCTAssertThrowsError(try implementation.configure(environment: "invalid", projectId: "project"))
    }
}
