package jp.albites.btree.view.screen.home.edit

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

class EditDialogModel(
    private val target: File,
    private val fileRepository: FileRepository
) : ScreenModel {
    private val _name: MutableStateFlow<String> = MutableStateFlow(
        target.name
    )
    val name: StateFlow<String> = _name

    private val _url: MutableStateFlow<String> = MutableStateFlow(
        target.asBookmark?.url ?: ""
    )
    val url: StateFlow<String> = _url

    val isBookmark: Boolean = target.isBookmark

    val isValid: StateFlow<Boolean> = name.combine(url) { name, url ->
        when(target) {
            is Bookmark -> {
                name.isNotEmpty() && url.isNotEmpty() && isUrlValid(url)
            }
            is Directory -> {
                name.isNotEmpty()
            }
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), false)

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateUrl(url: String) {
        _url.value = url
    }

    fun apply() {
        if (isValid.value) {
            when(target) {
                is Bookmark -> updateBookmark(target)
                is Directory -> updateDirectory(target)
            }
        }
    }

    private fun updateBookmark(target: Bookmark) {
        val newBookmark = target.copy(name = name.value, url = url.value)
        fileRepository.updateLeaf(newBookmark)
    }

    private fun updateDirectory(target: Directory) {
        val newDirectory = target.copy(name = name.value)
        fileRepository.updateLeaf(newDirectory)
    }

    private fun isUrlValid(url: String): Boolean {
        val regex = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
        return url.matches(regex.toRegex())
    }
}