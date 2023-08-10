package jp.albites.btree

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.ThemeRepository
import jp.albites.btree.view.screen.theme.ThemeScreenModel
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module


private const val THEME_PREFERENCES = "THEME_PREFERENCES"

actual val osModule : Module = module {
    factory {
        val context = get<Context>()
        val sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPreferences)
        ThemeRepository(settings = settings)
    }

    factory {
        val context = get<Context>()
        val sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPreferences)
        FileRepository(settings = settings)
    }
}