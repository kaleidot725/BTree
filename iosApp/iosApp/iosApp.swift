import ComposeApp
import SwiftUI
import UIKit

@main
struct iosApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    @State var isDarkMode: Bool = false

    var body: some View {
        ComposeView(
            onChangefdDarkMode: { b in isDarkMode = b }
        ).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/ .all/*@END_MENU_TOKEN@*/)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    let onChangefdDarkMode: (Bool) -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController(
            onChangedDarkMode: { kb in
                onChangefdDarkMode(kb == KotlinBoolean(bool: true))
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
