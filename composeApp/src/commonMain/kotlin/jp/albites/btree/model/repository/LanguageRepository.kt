package jp.albites.btree.model.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import jp.albites.btree.model.domain.Language
import kotlinx.coroutines.flow.map

class LanguageRepository(
    private val settings: ObservableSettings,
) {
    @OptIn(ExperimentalSettingsApi::class)
    private val languageText = settings.getStringFlow(LANGUAGE_KEY, Language.ENGLISH.text)
    val languageFlow get() = languageText.map { text ->
        Language.values().firstOrNull { it.text == text } ?: Language.ENGLISH
    }

    fun update(language: Language) {
        settings[LANGUAGE_KEY] = language.text
    }

    fun get(): Language {
        return settings[LANGUAGE_KEY] ?: Language.ENGLISH
    }

    companion object {
        private const val LANGUAGE_KEY = "language"
    }
}
