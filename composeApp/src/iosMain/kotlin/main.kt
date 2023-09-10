import androidx.compose.ui.window.ComposeUIViewController
import com.moriatsushi.insetsx.WindowInsetsUIViewController
import jp.albites.btree.appModule
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.osModule
import jp.albites.btree.util.openUrl
import jp.albites.btree.view.App
import platform.UIKit.UIViewController

fun MainViewController(
    onChangedDarkMode: (isDarkMode: Boolean) -> Unit
): UIViewController {
    return ComposeUIViewController {
        App(
            openUrl = { openUrl(it) },
            onChangedDarkMode = onChangedDarkMode,
            modules = listOf(
                appModule,
                osModule,
            )
        )
    }
}
