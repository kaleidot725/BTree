import androidx.compose.ui.window.ComposeUIViewController
import jp.albites.btree.App
import jp.albites.btree.openUrl
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App(
            openUrl = { openUrl(it) }
        )
    }
}
