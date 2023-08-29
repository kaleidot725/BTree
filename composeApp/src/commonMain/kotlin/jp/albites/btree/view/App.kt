package jp.albites.btree.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.model.repository.ThemeRepository
import jp.albites.btree.view.screen.home.HomeScreen
import jp.albites.btree.view.resources.AppTheme
import jp.albites.btree.view.resources.DarkColors
import jp.albites.btree.view.resources.LightColors
import kotlinx.coroutines.flow.Flow
import org.koin.mp.KoinPlatform

@Composable
internal fun App(openUrl: (String) -> Unit) {
    val theme by getThemeFlow().collectAsState(Theme.SYSTEM)
    val colorScheme = getColorScheme(theme)
    AppTheme(colorScheme) {
        Navigator(screen = HomeScreen(openUrl = openUrl))
    }
}

@Composable
private fun getThemeFlow(): Flow<Theme> {
    val koin = KoinPlatform.getKoin()
    return koin.get<ThemeRepository>().themeFlow
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
        Theme.LIGHT -> true
        Theme.SYSTEM -> isSystemInDarkTheme()
    }
}

