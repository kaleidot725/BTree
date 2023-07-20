package jp.albites.btree.component.explorer

data class File(
    val isDirectory: Boolean = false,
    val listFiles: List<File> = emptyList(),
    val name: String = "TEST",
    val url: String = "https://2023.droidkaigi.jp/"
)