package jp.albites.btree.view.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class HomeScreenModel(
    private val fileRepository: FileRepository
) : ScreenModel {
    val fileTree: StateFlow<File> = fileRepository.fileFlow.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        Directory.ROOT
    )

    private val _selectedFile: MutableStateFlow<File> = MutableStateFlow(fileTree.value)
    val selectedFile: StateFlow<File> = _selectedFile.asStateFlow()

    private val _expandedDirs: MutableStateFlow<List<Directory>> = MutableStateFlow(emptyList())
    val expandedDirs: StateFlow<List<Directory>> = _expandedDirs.asStateFlow()

    fun onClickArrow(directory: Directory) {
        val current = _expandedDirs.value
        val isExpanded = current.any { it.id == directory.id }
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
