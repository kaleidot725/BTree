import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import jp.albites.btree.appModule
import jp.albites.btree.osModule
import jp.albites.btree.util.openUrl
import jp.albites.btree.view.App

fun main() {
    return application {
        Window(
            title = "BTree",
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            onCloseRequest = ::exitApplication,
        ) {
            App(
                openUrl = { openUrl(it) },
                modules = listOf(
                    appModule,
                    osModule,
                ),
            )
        }
    }
}
