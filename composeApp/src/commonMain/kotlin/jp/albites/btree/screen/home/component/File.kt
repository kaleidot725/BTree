package jp.albites.btree.screen.home.component

sealed class File(
    open val name: String
) {
    val isDirectory get() = this is Directory
    val asDirectory get() = this as? Directory
    val isBookmark get() = this is Bookmark
    val asBookmark get() = this as? Bookmark
}

data class Directory(
    override val name: String,
    val list: List<File>,
) : File(name)

data class Bookmark(
    override val name: String,
    val url: String
) : File(name)