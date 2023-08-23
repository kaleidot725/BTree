package jp.albites.btree.view.screen.home.bookmark

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BookmarkDialogModel(
    private val targetId: String,
    private val fileRepository: FileRepository
) : ScreenModel {
    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _url: MutableStateFlow<String> = MutableStateFlow("")
    val url: StateFlow<String> = _url

    val isValid: StateFlow<Boolean> = name.combine(url) { name, url ->
        when {
            name.isEmpty() -> false
            url.isEmpty() || !isUrlValid(url) -> false
            else -> true
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), false)

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateUrl(url: String) {
        _url.value = url
    }

    fun register() {
        if (isValid.value) {
            if (targetId == Directory.ROOT.id) {
                val root = fileRepository.getRoot()
                val newBookmark = Bookmark(name = name.value, url = url.value) as File
                val newBookmarks = root.list + newBookmark
                val newRoot = root.copy(list = newBookmarks)
                fileRepository.updateRoot(newRoot)
            } else {
                val leaf = fileRepository.getLeaf(targetId)?.asDirectory ?: return
                val newBookmark = Bookmark(name = name.value, url = url.value) as File
                val newBookmarks = leaf.list + newBookmark
                val newLeaf = leaf.copy(list = newBookmarks)
                fileRepository.updateLeaf(newLeaf)
            }
        }
        _name.value = ""
        _url.value = ""
    }

    private fun isUrlValid(url: String): Boolean {
        val regex = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
        return url.matches(regex.toRegex())
    }
}