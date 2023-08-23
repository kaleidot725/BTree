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
    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name

    val isValid: StateFlow<Boolean> = name.map { name ->
        when {
            name.isEmpty() -> false
            else -> true
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), false)

    fun updateName(name: String) {
        _name.value = name
    }

    fun register() {
        if (isValid.value) {
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

        _name.value = ""
    }
}