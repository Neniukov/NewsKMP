import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        KoinIos().doInitKoin()
        }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
