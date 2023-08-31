import com.moriatsushi.insetsx.WindowInsetsUIViewController
import jp.albites.btree.util.openUrl
import jp.albites.btree.view.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return WindowInsetsUIViewController {
        App(openUrl = { openUrl(it) })
    }
}
