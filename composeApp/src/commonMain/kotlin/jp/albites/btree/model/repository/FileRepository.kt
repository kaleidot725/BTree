package jp.albites.btree.model.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.util.randomUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FileRepository(
    private val settings: ObservableSettings
) {
    @OptIn(ExperimentalSettingsApi::class)
    private val file  = settings.getStringOrNullFlow(FILE_LIST_KEY)

    val fileFlow: Flow<File>
        get() = file.map { text ->
            if (text != null) {
                val fileData = Json.decodeFromString<FileData>(text)
                convertFile(fileData)
            } else {
                Directory.ROOT
            }
        }

    fun update(file: File) {
        val fileData = convertFileData(file)
        val text = Json.encodeToString(fileData)
        settings[FILE_LIST_KEY] = text
    }

    fun get() : File {
        val text : String? = settings[FILE_LIST_KEY]
        return if (text != null) {
            val fileData = Json.decodeFromString<FileData>(text)
            convertFile(fileData)
        } else {
            Directory.ROOT
        }
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
        val id: String = randomUUID(),
        val name: String,
        val isDirectory: Boolean,
        val list: List<FileData> = emptyList(),
        val url: String? = null
    )

    companion object {
        private const val FILE_LIST_KEY = "file_list"
    }
}