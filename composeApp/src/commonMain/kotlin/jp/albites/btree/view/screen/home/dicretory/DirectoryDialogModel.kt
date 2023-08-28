package jp.albites.btree.view.screen.home.dicretory

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DirectoryDialogModel(
    private val targetId: String,
    private val fileRepository: FileRepository
) : ScreenModel {
    private val name: MutableStateFlow<String> = MutableStateFlow("")
    val state: StateFlow<State> = name.map { name ->
        val isValid = when {
            name.isEmpty() -> false
            else -> true
        }
        State(name, isValid)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State())

    fun updateName(name: String) {
        this.name.value = name
    }

    fun register() {
        if (state.value.isValid) {
            if (targetId == Directory.ROOT.id) {
                val root = fileRepository.getRoot()
                val newDirectory = Directory(name = name.value, list = emptyList()) as File
                val newFiles = root.list + newDirectory
                val newRoot = root.copy(list = newFiles)
                fileRepository.updateRoot(newRoot)
            } else {
                val leaf = fileRepository.getLeaf(targetId)?.asDirectory ?: return
                val newDirectory = Directory(name = name.value, list = emptyList()) as File
                val newFiles = leaf.list + newDirectory
                val newLeaf = leaf.copy(list = newFiles)
                fileRepository.updateLeaf(newLeaf)
            }
        }

        name.value = ""
    }

    data class State(
        val name: String = "",
        val isValid: Boolean = false,
    )
}