package jp.albites.btree

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import jp.albites.btree.util.openUrl
import jp.albites.btree.view.App

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            Box(modifier = Modifier.navigationBarsPadding()) {
                App(openUrl = { openUrl(it) })
            }
        }
    }
}