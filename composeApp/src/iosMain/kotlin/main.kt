import androidx.compose.ui.window.ComposeUIViewController
import jp.albites.btree.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
