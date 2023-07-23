import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import jp.albites.btree.App
import jp.albites.btree.openUrl

fun main() = application {
    Window(
        title = "BTree",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App(
            openUrl = { openUrl(it) }
        )
    }
}