package jp.albites.btree

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.LanguageRepository
import jp.albites.btree.model.repository.ThemeRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

val osModule: Module = module {
    factory {
        val delegate: Preferences = Preferences.userRoot()
        val settings: ObservableSettings = PreferencesSettings(delegate)
        ThemeRepository(settings = settings)
    }

    factory {
        val delegate: Preferences = Preferences.userRoot()
        val settings: ObservableSettings = PreferencesSettings(delegate)
        FileRepository(settings = settings)
    }

    factory {
        val delegate: Preferences = Preferences.userRoot()
        val settings: ObservableSettings = PreferencesSettings(delegate)
        LanguageRepository(settings = settings)
    }
}
