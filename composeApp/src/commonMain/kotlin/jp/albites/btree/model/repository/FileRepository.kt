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
        val fileData = convertFileData(file)
        val text = Json.encodeToString(fileData)
        settings[FILE_LIST_KEY] = text
    }

    fun updateLeaf(file: File) {
        val target = convertFileData(get())
        val leaf = convertFileData(file)
        replaceLeaf(target, leaf)

        val text = Json.encodeToString(target)
        settings[FILE_LIST_KEY] = text
    }

    fun get(): File {
        val text: String? = settings[FILE_LIST_KEY]
        return if (text != null) {
            val fileData = Json.decodeFromString<FileData>(text)
            convertFile(fileData)
        } else {
            Directory.ROOT
        }
    }

    private fun replaceLeaf(target: FileData, leaf: FileData) {
        val hasLeaf = target.list.any { it.id == leaf.id }
        if (!hasLeaf) return

        val index = target.list.indexOfFirst { it.id == leaf.id }
        val newLeaf = target.list.toMutableList()
        newLeaf.removeAt(index)
        newLeaf.add(index, leaf)
        target.list = newLeaf
    }

    private fun convertFileData(file: File): FileData {
        val id = file.id
        val name = file.name
        val url = file.asBookmark?.url
        val isDirectory = file.isDirectory
        val list = (file.asDirectory?.list ?: emptyList()).map {
            convertFileData(it)
        }
        return FileData(
            id = id,
            name = name,
            isDirectory = isDirectory,
            list = list,
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