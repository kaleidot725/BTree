package jp.albites.btree.view.screen.home.delete

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeleteDialogModel(
    private val targetId: String,
    private val fileRepository: FileRepository,
) : ScreenModel {
    private val file: MutableStateFlow<File> = MutableStateFlow(File.NONE)
    val state: StateFlow<State> = file.map { file ->
        State(file)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), State())

    init {
        coroutineScope.launch {
            file.value = fileRepository.getLeaf(targetId) ?: File.NONE
        }
    }

    fun delete() {
        fileRepository.deleteLeaf(targetId)
    }

    data class State(
        val file: File = File.NONE,
    )
}
