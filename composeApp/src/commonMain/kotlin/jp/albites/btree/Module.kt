package jp.albites.btree

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.view.screen.home.HomeScreenModel
import jp.albites.btree.view.screen.home.bookmark.BookmarkDialogModel
import jp.albites.btree.view.screen.home.dicretory.DirectoryDialogModel
import jp.albites.btree.view.screen.theme.ThemeScreenModel
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

expect val osModule : Module

val appModule: Module = module {
    factory {
        ThemeScreenModel(get())
    }

    factory {
        HomeScreenModel(get())
    }

    factory { (dir: Directory) ->
        BookmarkDialogModel(dir, get())
    }

    factory {
        DirectoryDialogModel(get())
    }
}

val allModule get() = listOf(appModule, osModule)

@Composable
inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = KoinPlatform.getKoin()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}

@Composable
inline fun <reified T : ScreenModel> Screen.getDialogScreenModel(
    tag: String,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = KoinPlatform.getKoin()
    return rememberScreenModel(tag = tag) { koin.get(qualifier, parameters) }
}