package jp.albites.btree

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.ThemeRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val osModule: Module = module {
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
}
