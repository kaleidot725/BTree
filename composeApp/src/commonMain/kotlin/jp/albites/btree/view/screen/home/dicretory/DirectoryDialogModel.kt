package jp.albites.btree.view.screen.home.dicretory

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DirectoryDialogModel(
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

    fun create() {
        if (isValid.value) {
            val root = fileRepository.get().asDirectory!!
            val newBookmark = Directory(name = name.value, list = emptyList()) as File
            val newBookmarks = root.list + newBookmark
            val newRoot = root.copy(list = newBookmarks)
            fileRepository.update(newRoot)
        }
    }
}