package jp.albites.btree.model.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FileRepository(
    private val settings: ObservableSettings
) {
    @OptIn(ExperimentalSettingsApi::class)
    private val file = settings.getStringOrNullFlow(FILE_LIST_KEY)

    val fileFlow: Flow<File>
        get() = file.map { text ->
            if (text != null) {
                val fileData = Json.decodeFromString<FileData>(text)
                convertFile(fileData)
            } else {
                Directory.ROOT
            }
        }

    fun updateRoot(file: File) {
        val root = convertFileData(file)
        val text = Json.encodeToString(root)
        settings[FILE_LIST_KEY] = text
    }

    fun updateLeaf(file: File) {
        val target = convertFileData(getRoot())
        val leaf = convertFileData(file)
        val result = replaceLeaf(target, leaf)
        if (result) {
            val text = Json.encodeToString(target)
            settings[FILE_LIST_KEY] = text
        }
    }

    fun getLeaf(id: String): File? {
        val target = convertFileData(getRoot())
        val latestLeafData = findLeafFromFileData(target, id) ?: return null
        return convertFile(latestLeafData)
    }

    fun deleteLeaf(id : String) {
        val target = convertFileData(getRoot())
        val result = deleteLeaf(target, id)
        if (result) {
            val text = Json.encodeToString(target)
            settings[FILE_LIST_KEY] = text
        }
    }

    fun getRoot(): Directory {
        val text: String? = settings[FILE_LIST_KEY]
        return if (text != null) {
            val fileData = Json.decodeFromString<FileData>(text)
            convertFile(fileData)?.asDirectory ?: Directory.ROOT
        } else {
            Directory.ROOT
        }
    }

    private fun deleteLeaf(target: FileData, id: String): Boolean {
        val isEmptyLeaf = target.list.isEmpty()
        if (isEmptyLeaf) return false

        val hasNotLeaf = !target.list.any { it.id == id }
        if (hasNotLeaf) {
            for (i in 0..target.list.lastIndex) {
                val success = deleteLeaf(target.list[i], id)
                if (success) return true
            }
            return false
        } else {
            val index = target.list.indexOfFirst { it.id == id }
            val newLeaf = target.list.toMutableList()
            newLeaf.removeAt(index)
            target.list = newLeaf
            return true
        }
    }

    private fun replaceLeaf(target: FileData, leaf: FileData): Boolean {
        val isEmptyLeaf = target.list.isEmpty()
        if (isEmptyLeaf) return false

        val hasNotLeaf = !target.list.any { it.id == leaf.id }
        if (hasNotLeaf) {
            for (i in 0..target.list.lastIndex) {
                val success = replaceLeaf(target.list[i], leaf)
                if (success) return true
            }
            return false
        } else {
            val index = target.list.indexOfFirst { it.id == leaf.id }
            val newLeaf = target.list.toMutableList()

            newLeaf.removeAt(index)
            newLeaf.add(index, leaf)

            target.list = newLeaf
            return true
        }
    }

    private fun findLeafFromFileData(target: FileData, id: String): FileData? {
        val isEmptyLeaf = target.list.isEmpty()
        if (isEmptyLeaf) return null

        val targetLeaf = target.list.firstOrNull { it.id == id }
        if (targetLeaf == null) {
            for (i in 0..target.list.lastIndex) {
                val targetLeafR = findLeafFromFileData(target.list[i], id)
                if (targetLeafR != null) return targetLeafR
            }
            return null
        } else {
            return targetLeaf
        }
    }

    private fun convertFileData(file: File): FileData {
        val id = file.id
        val name = file.name
        val url = file.asBookmark?.url
        val isDirectory = file.isDirectory
        val directoryList = file.asDirectory?.list ?: emptyList()
        val directoryFileDataList = directoryList.map { convertFileData(it) }

        return FileData(
            id = id,
            name = name,
            isDirectory = isDirectory,
            list = directoryFileDataList,
            url = url
        )
    }

    private fun convertFile(fileData: FileData): File {
        return if (fileData.isDirectory) {
            return Directory(
                id = fileData.id,
                name = fileData.name,
                list = fileData.list.map { convertFile(it) }
            )
        } else {
            return Bookmark(
                id = fileData.id,
                name = fileData.name,
                url = fileData.url!!
            )
        }
    }

    @Serializable
    private data class FileData(
        val id: String,
        val name: String,
        val isDirectory: Boolean,
        var list: List<FileData> = emptyList(),
        val url: String? = null
    )

    companion object {
        private const val FILE_LIST_KEY = "file_list"
    }
}