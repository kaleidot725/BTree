package jp.albites.btree.view.screen.home.register

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class RegisterDialogModel(
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
            val root = fileRepository.get().asDirectory!!
            val newBookmark = Bookmark(name = name.value, url = url.value) as File
            val newBookmarks = root.list + newBookmark
            val newRoot = root.copy(list = newBookmarks)
            fileRepository.update(newRoot)
        }
    }

    private fun isUrlValid(url: String): Boolean {
        val regex = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
        return url.matches(regex.toRegex())
    }
}