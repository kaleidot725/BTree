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
import kotlinx.coroutines.launch

class EditDialogModel(
    private val targetId: String,
    private val fileRepository: FileRepository
) : ScreenModel {
    private val target: MutableStateFlow<File> = MutableStateFlow(File.NONE)

    private val name: MutableStateFlow<String> = MutableStateFlow("")

    private val url: MutableStateFlow<String> = MutableStateFlow("")

    val state: StateFlow<State> = combine(target, name, url) { target, name, url ->
        val isValid = when (target) {
            is Bookmark -> {
                name.isNotEmpty() && url.isNotEmpty() && isUrlValid(url)
            }

            is Directory -> {
                name.isNotEmpty()
            }

            else -> false
        }
        State(target, name, url, isValid)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State())

    init {
        coroutineScope.launch {
            val leaf = fileRepository.getLeaf(targetId) ?: return@launch
            target.value = leaf
            name.value = leaf.name
            url.value = leaf.asBookmark?.url ?: ""
        }
    }

    fun updateName(name: String) {
        this.name.value = name
    }

    fun updateUrl(url: String) {
        this.url.value = url
    }

    fun apply() {
        if (state.value.isValid) {
            val value = target.value
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

    data class State(
        val file: File = File.NONE,
        val name: String = "",
        val url: String = "",
        val isValid: Boolean = false
    )
}