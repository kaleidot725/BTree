package jp.albites.btree

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.russhwolf.settings.SharedPreferencesSettings
import jp.albites.btree.model.repository.FileRepository
import jp.albites.btree.model.repository.LanguageRepository
import jp.albites.btree.model.repository.LicenseRepository
import jp.albites.btree.model.repository.ThemeRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

private const val THEME_PREFERENCES = "THEME_PREFERENCES"
private const val FILE_PREFERENCES = "FILE_PREFERENCES"
private const val LANGUAGE_PREFERENCES = "LANGUAGE_PREFERENCES"

@OptIn(ExperimentalResourceApi::class)
fun osModule(context: Context): Module = module {
    factory {
        val sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPreferences)
        ThemeRepository(settings = settings)
    }

    factory {
        val sharedPreferences = context.getSharedPreferences(FILE_PREFERENCES, MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPreferences)
        FileRepository(settings = settings)
    }

    factory {
        val sharedPreferences = context.getSharedPreferences(LANGUAGE_PREFERENCES, MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPreferences)
        LanguageRepository(settings = settings)
    }

    factory {
        val resource = resource("android/artifacts.json")
        LicenseRepository(resource)
    }
}
