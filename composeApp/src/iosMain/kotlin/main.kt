import androidx.compose.ui.uikit.ComposeUIViewControllerConfiguration
import androidx.compose.ui.window.ComposeUIViewController
import com.moriatsushi.insetsx.WindowInsetsUIViewController
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

    return WindowInsetsUIViewController {
        App(openUrl = { openUrl(it) })
    }
}
