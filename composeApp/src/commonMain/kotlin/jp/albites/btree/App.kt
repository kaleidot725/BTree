package jp.albites.btree

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.resource.theme.AppTheme
import jp.albites.btree.screen.home.HomeScreen
import jp.albites.btree.screen.setting.SettingScreen

@Composable
internal fun App(openUrl: (String) -> Unit) = AppTheme {
    Navigator(
        HomeScreen(openUrl = openUrl)
    )
}

internal expect fun openUrl(url: String?)