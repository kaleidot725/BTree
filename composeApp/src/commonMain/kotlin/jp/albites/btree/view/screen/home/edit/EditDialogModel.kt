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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditDialogModel(
    private val targetId: String,
    private val fileRepository: FileRepository
) : ScreenModel {
    private val _target: MutableStateFlow<File?> = MutableStateFlow(null)
    val target: StateFlow<File?> = _target.asStateFlow()

    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _url: MutableStateFlow<String> = MutableStateFlow("")
    val url: StateFlow<String> = _url.asStateFlow()

    val isValid: StateFlow<Boolean> = combine(_target, name, url) { target, name, url ->
        when (target) {
            is Bookmark -> {
                name.isNotEmpty() && url.isNotEmpty() && isUrlValid(url)
            }

            is Directory -> {
                name.isNotEmpty()
            }

            else -> false
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), false)

    init {
        coroutineScope.launch {
            val leaf = fileRepository.getLeaf(targetId) ?: return@launch
            _target.value = leaf
            _name.value = leaf.name
            _url.value = leaf.asBookmark?.url ?: ""
        }
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateUrl(url: String) {
        _url.value = url
    }

    fun apply() {
        if (isValid.value) {
            val value = _target.value ?: return
            when (value) {
                is Bookmark -> updateBookmark(value)
                is Directory -> updateDirectory(value)
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