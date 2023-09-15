package jp.albites.btree

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.LanguageRepository
import jp.albites.btree.model.repository.LicenseRepository
import jp.albites.btree.model.repository.ThemeRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalResourceApi::class)
val osModule: Module = module {
    factory {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
        val settings: ObservableSettings = NSUserDefaultsSettings(delegate)
        ThemeRepository(settings = settings)
    }
    factory {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
        val settings: ObservableSettings = NSUserDefaultsSettings(delegate)
        FileRepository(settings = settings)
    }
    factory {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
        val settings: ObservableSettings = NSUserDefaultsSettings(delegate)
        LanguageRepository(settings = settings)
    }

    factory {
        val text = resource("licensee/ios/artifacts.json")
        LicenseRepository(text)
    }
}
