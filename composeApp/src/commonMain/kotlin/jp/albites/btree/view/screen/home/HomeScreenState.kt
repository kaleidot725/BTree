package jp.albites.btree.view.screen.home

import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File

data class HomeScreenState(
    val fileTree: File = Directory.ROOT,
    val selectedFile: File = Directory.ROOT,
    val expandedDirs: List<Directory> = emptyList(),
)