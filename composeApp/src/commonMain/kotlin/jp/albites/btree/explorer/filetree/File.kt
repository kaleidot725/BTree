package jp.albites.btree.explorer.filetree

data class File(
    val isDirectory: Boolean = false,
    val listFiles: List<File> = emptyList(),
    val name: String = "TEST",
    val url: String = "TEST"
)