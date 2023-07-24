package jp.albites.btree

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import jp.albites.btree.model.repository.ThemeRepository
import jp.albites.btree.view.screen.theme.ThemeScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val osModule : Module = module {
    factory {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
        val settings: ObservableSettings = NSUserDefaultsSettings(delegate)
        ThemeRepository(settings = settings)
    }
}