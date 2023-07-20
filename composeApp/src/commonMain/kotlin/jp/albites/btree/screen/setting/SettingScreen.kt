package jp.albites.btree.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class SettingScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SettingScreenModel() }
        Box(Modifier.fillMaxSize().background(Color.Red)) {
            Text(screenModel.text)
        }
    }
}