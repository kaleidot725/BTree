package jp.albites.btree.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import com.moriatsushi.insetsx.rememberWindowInsetsController
import jp.albites.btree.allModule
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.model.repository.ThemeRepository
import jp.albites.btree.view.resources.AppTheme
import jp.albites.btree.view.resources.DarkColors
import jp.albites.btree.view.resources.LightColors
import jp.albites.btree.view.screen.home.HomeScreen
import kotlinx.coroutines.flow.Flow
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
internal fun App(openUrl: (String) -> Unit) {
    KoinApplication(application = { modules(allModule) }) {
        val theme by getThemeFlow().collectAsState(Theme.SYSTEM)
        val isDarkMode = isDarkMode(theme)
        val colorScheme = getColorScheme(theme)
        val windowInsetsController = rememberWindowInsetsController()

        LaunchedEffect(isDarkMode) {
            windowInsetsController?.setStatusBarContentColor(dark = !isDarkMode)
            windowInsetsController?.setNavigationBarsContentColor(dark = !isDarkMode)
        }

        AppTheme(colorScheme) {
            Navigator(screen = HomeScreen(openUrl = openUrl))
        }
    }
}

@Composable
private fun getThemeFlow(): Flow<Theme> {
    return koinInject<ThemeRepository>().themeFlow
}

@Composable
private fun getColorScheme(theme: Theme): ColorScheme {
    return when (theme) {
        Theme.DARK -> DarkColors
        Theme.LIGHT -> LightColors
        Theme.SYSTEM -> if (isSystemInDarkTheme()) DarkColors else LightColors
    }
}

@Composable
fun isDarkMode(theme: Theme): Boolean {
    return when (theme) {
        Theme.DARK -> true
        Theme.LIGHT -> false
        Theme.SYSTEM -> isSystemInDarkTheme()
    }
}
