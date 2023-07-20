package jp.albites.btree

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import jp.albites.btree.resource.theme.AppTheme
import jp.albites.btree.screen.home.HomeScreen

@Composable
internal fun App(openUrl: (String) -> Unit) = AppTheme {
    Navigator(HomeScreen(openUrl))
}

internal expect fun openUrl(url: String?)