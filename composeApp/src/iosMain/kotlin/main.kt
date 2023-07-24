import androidx.compose.ui.window.ComposeUIViewController
import jp.albites.btree.App
import jp.albites.btree.allModule
import jp.albites.btree.osModule
import jp.albites.btree.openUrl
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        modules(allModule)
    }

    return ComposeUIViewController {
        App(
            openUrl = { openUrl(it) }
        )
    }
}
