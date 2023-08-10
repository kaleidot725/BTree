package jp.albites.btree.model.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
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
    private val file = settings.getStringFlow(FILE_LIST_KEY, "{}")
    val fileFlow: Flow<File>
        get() = file.map { text ->
            val fileData = Json.decodeFromString<FileData>(text)
            convertFile(fileData)
        }

    fun update(file: File) {
        val fileData = convertFileData(file)
        val text = Json.encodeToString(fileData)
        settings[FILE_LIST_KEY] = text
    }

    private fun convertFileData(file: File): FileData {
        val name = file.name
        val url = file.asBookmark?.url
        val isDirectory = file.isDirectory
        val list = (file.asDirectory?.list ?: emptyList()).map {
            convertFileData(it)
        }
        return FileData(
            name = name,
            isDirectory = isDirectory,
            list = list,
            url = url
        )
    }

    private fun convertFile(fileData: FileData): File {
        return if (fileData.isDirectory) {
            return Directory(
                name = fileData.name,
                list = fileData.list.map { convertFile(it) }
            )
        } else {
            return Bookmark(
                name = fileData.name,
                url = fileData.url!!
            )
        }
    }

    @Serializable
    private data class FileData(
        val name: String,
        val isDirectory: Boolean,
        val list: List<FileData> = emptyList(),
        val url: String? = null
    )

    companion object {
        private const val FILE_LIST_KEY = "file_list"
    }
}