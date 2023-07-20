package jp.albites.btree.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import jp.albites.btree.component.explorer.File

class HomeScreenModel : ScreenModel {
    val fileTree: File = File(
        isDirectory = true,
        listFiles = listOf(
            File(
                isDirectory = true,
                listFiles = listOf(
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "Google Store",
                        url = "https://store.google.com"
                    ),
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "Google Search",
                        url = "https://google.com"
                    )
                ),
                name = "GOOGLE"
            ),
            File(
                isDirectory = true,
                listFiles = listOf(
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "Apple Store",
                        url = "https://www.apple.com/jp/store"
                    ),
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "SwiftUI",
                        url = "https://developer.apple.com/jp/xcode/swiftui/"
                    )
                ),
                name = "APPLE"
            )
        ),
        name = "ROOT"
    )
}
