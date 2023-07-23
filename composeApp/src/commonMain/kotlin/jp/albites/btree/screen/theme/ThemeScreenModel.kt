package jp.albites.btree.screen.theme

import cafe.adriel.voyager.core.model.ScreenModel
import jp.albites.btree.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeScreenModel : ScreenModel {
    private val _selectedTheme: MutableStateFlow<Theme>  = MutableStateFlow(Theme.SYSTEM)
    val selectedTheme: StateFlow<Theme> = _selectedTheme.asStateFlow()

    fun selectTheme(theme: Theme) {
        _selectedTheme.value = theme
    }
}