package jp.albites.btree.model.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import jp.albites.btree.model.domain.Theme
import kotlinx.coroutines.flow.map

class ThemeRepository(
    private val settings: ObservableSettings
) {
    @OptIn(ExperimentalSettingsApi::class)
    private val themeText = settings.getStringFlow(THEME_KEY, Theme.SYSTEM.text)
    val themeFlow get() = themeText.map { themeText ->
        Theme.values().firstOrNull { it.text ==  themeText} ?: Theme.SYSTEM
    }

    fun update(theme: Theme) {
        settings[THEME_KEY] = theme.text
    }

    fun get() : Theme {
        return settings[THEME_KEY] ?: Theme.SYSTEM
    }

    companion object {
        private const val THEME_KEY = "theme"
    }
}