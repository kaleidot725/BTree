package jp.albites.btree.view.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeScreenModel(
    fileRepository: FileRepository,
) : ScreenModel {
    private val fileTree: StateFlow<File> = fileRepository.fileFlow.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        Directory.ROOT,
    )
    private val selectedFile: MutableStateFlow<File> = MutableStateFlow(fileTree.value)
    private val expandedDirs: MutableStateFlow<List<Directory>> = MutableStateFlow(emptyList())
    val state = combine(fileTree, selectedFile, expandedDirs) { fileTree, selectedFile, expandedDirs ->
        val directory = fileTree.asDirectory ?: Directory.ROOT
        val latestFile = fileRepository.getLeaf(selectedFile.id) ?: Directory.ROOT
        State(
            fileTree = directory,
            selectedFile = latestFile,
            expandedDirs = expandedDirs,
            canEdit = latestFile.id != Directory.ROOT.id,
            canDelete = latestFile.id != Directory.ROOT.id,
            canAddBookmark = latestFile.isDirectory,
            canCreateNewFolder = latestFile.isDirectory,
        )
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State())

    fun onClickArrow(directory: Directory) {
        val current = expandedDirs.value
        val isExpanded = current.any { it.id == directory.id }
        if (isExpanded) {
            val bottomDirectories = mutableListOf(directory)
            collectDirectories(bottomDirectories, directory)

            val newExpandedDirs = expandedDirs.value.toMutableList()
            newExpandedDirs.removeAll { target ->
                bottomDirectories.any { target.id == it.id }
            }

            expandedDirs.value = newExpandedDirs
        } else {
            expandedDirs.value = current + directory
        }
    }

    fun onClickFile(file: File) {
        selectedFile.value = file
    }

    fun onResetFile() {
        selectedFile.value = Directory.ROOT
    }

    private fun collectDirectories(list: MutableList<Directory>, target: Directory) {
        target.list.mapNotNull { it.asDirectory }.forEach {
            list.add(it)
            collectDirectories(list, it)
        }
    }

    data class State(
        val fileTree: Directory = Directory.ROOT,
        val selectedFile: File = File.NONE,
        val expandedDirs: List<Directory> = emptyList(),
        val canCreateNewFolder: Boolean = true,
        val canAddBookmark: Boolean = true,
        val canDelete: Boolean = false,
        val canEdit: Boolean = false,
    )
}
