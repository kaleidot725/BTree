package jp.albites.btree.view.screen.theme

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Language
import jp.albites.btree.model.repository.LanguageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LanguageScreenModel(
    private val languageRepository: LanguageRepository,
) : ScreenModel {
    val selectedLanguage: StateFlow<Language?> = languageRepository.languageFlow.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        null,
    )

    fun selectLanguage(language: Language) {
        languageRepository.update(language)
    }
}
