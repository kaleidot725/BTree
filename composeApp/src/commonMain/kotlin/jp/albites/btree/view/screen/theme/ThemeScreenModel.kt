package jp.albites.btree.view.screen.theme

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.model.repository.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ThemeScreenModel(
    private val themeRepository: ThemeRepository,
) : ScreenModel {
    val selectedTheme: StateFlow<Theme?> = themeRepository.themeFlow.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        null,
    )

    fun selectTheme(theme: Theme) {
        themeRepository.update(theme)
    }
}
