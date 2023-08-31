package jp.albites.btree

import jp.albites.btree.view.screen.home.HomeScreenModel
import jp.albites.btree.view.screen.home.bookmark.BookmarkDialogModel
import jp.albites.btree.view.screen.home.delete.DeleteDialogModel
import jp.albites.btree.view.screen.home.dicretory.DirectoryDialogModel
import jp.albites.btree.view.screen.home.edit.EditDialogModel
import jp.albites.btree.view.screen.theme.ThemeScreenModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect val osModule: Module

val appModule: Module = module {
    factory {
        ThemeScreenModel(get())
    }

    factory {
        HomeScreenModel(get())
    }

    factory { (dirId: String) ->
        BookmarkDialogModel(dirId, get())
    }

    factory { (dirId: String) ->
        DirectoryDialogModel(dirId, get())
    }

    factory { (fileId: String) ->
        DeleteDialogModel(fileId, get())
    }

    factory { (fileId: String) ->
        EditDialogModel(fileId, get())
    }
}

val allModule get() = listOf(appModule, osModule)
