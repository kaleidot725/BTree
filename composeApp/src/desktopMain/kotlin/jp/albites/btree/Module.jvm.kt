package jp.albites.btree

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.LanguageRepository
import jp.albites.btree.model.repository.LicenseRepository
import jp.albites.btree.model.repository.ThemeRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

@OptIn(ExperimentalResourceApi::class)
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

    factory {
        val resource = resource("desktop/artifacts.json")
        LicenseRepository(resource)
    }
}
