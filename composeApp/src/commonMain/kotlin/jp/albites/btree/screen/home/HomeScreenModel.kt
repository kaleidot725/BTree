package jp.albites.btree.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import jp.albites.btree.component.explorer.Bookmark
import jp.albites.btree.component.explorer.Directory
import jp.albites.btree.component.explorer.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenModel : ScreenModel {
    private val _fileTree: MutableStateFlow<File> = MutableStateFlow(
        Directory(
            list = listOf(
                Directory(
                    list = listOf(
                        Bookmark(
                            name = "Google Store",
                            url = "https://store.google.com"
                        ),
                        Bookmark(
                            name = "Google Search",
                            url = "https://google.com"
                        )
                    ),
                    name = "Google",
                ),
                Directory(
                    list = listOf(
                        Bookmark(
                            name = "Apple Store",
                            url = "https://www.apple.com/jp/store"
                        ),
                        Bookmark(
                            name = "SwiftUI",
                            url = "https://developer.apple.com/jp/xcode/swiftui/"
                        )
                    ),
                    name = "APPLE"
                )
            ),
            name = "ROOT"
        )
    )
    val fileTree: StateFlow<File> = _fileTree.asStateFlow()

    private val _selectedFile: MutableStateFlow<File> = MutableStateFlow(_fileTree.value)
    val selectedFile: StateFlow<File> = _selectedFile.asStateFlow()

    private val _expandedDirs: MutableStateFlow<List<Directory>> = MutableStateFlow(emptyList())
    val expandedDirs: StateFlow<List<Directory>> = _expandedDirs.asStateFlow()

    fun onClickArrow(directory: Directory) {
        val current = _expandedDirs.value
        val isExpanded = current.any { it == directory }
        if (isExpanded) {
            val bottomDirectories = mutableListOf<Directory>()
            collectDirectories(bottomDirectories, directory)
            _expandedDirs.value = current - bottomDirectories.toSet() - directory
        } else {
            _expandedDirs.value = current + directory
        }
    }

    fun onClickFile(file: File) {
        _selectedFile.value = file
    }

    private fun collectDirectories(list: MutableList<Directory>, target: Directory) {
        target.list.mapNotNull { it.asDirectory }.forEach {
            list.add(it)
            collectDirectories(list, it)
        }
    }
}
