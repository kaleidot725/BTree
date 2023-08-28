package jp.albites.btree.model.domain

import jp.albites.btree.util.randomUUID


sealed class File(
    open val id: String = randomUUID(),
    open val name: String
) {
    val isDirectory get() = this is Directory
    val asDirectory get() = this as? Directory
    val isBookmark get() = this is Bookmark
    val asBookmark get() = this as? Bookmark

    companion object {
        val NONE = Directory("", "", emptyList())
    }
}

data class Directory(
    override val id: String = randomUUID(),
    override val name: String,
    val list: List<File>,
) : File(id, name) {
    companion object {
        val ROOT = Directory("ROOT", "ROOT", emptyList())
    }
}

data class Bookmark(
    override val id: String = randomUUID(),
    override val name: String,
    val url: String
) : File(id, name)