package jp.albites.btree.view.resources

import androidx.compose.runtime.Composable

object StringResource : StringList by StringListDefault

private object StringListDefault : StringList {
    // Home
    @Composable
    override fun homeTitle(): String = "BTree"

    @Composable
    override fun homeCreateFolder(): String = "Create Folder"

    @Composable
    override fun homeAddBookmark(): String = "Add Bookmark"

    @Composable
    override fun homeEdit(): String = "Edit"

    @Composable
    override fun homeDelete(): String = "Delete"

    // Edit
    @Composable
    override fun editTitle(name: String): String = "Edit $name"

    @Composable
    override fun editNamePlaceHolder(): String = "Name"

    @Composable
    override fun editUrlPlaceHolder(): String = "URL"

    // Directory
    @Composable
    override fun directoryTitle(): String = "Create Folder"

    @Composable
    override fun directoryNamePlaceHolder(): String = "Name"

    @Composable
    override fun deleteTitle(name: String): String = "Delete $name"

    @Composable
    override fun deleteMessage(name: String): String = "Do you delete $name?"

    // Bookmark
    @Composable
    override fun bookmarkTitle(): String = "Create Link"

    @Composable
    override fun bookmarkNamePlaceHolder(): String = "Name"

    @Composable
    override fun bookmarkUrlPlaceHolder(): String = "URL"

    // Setting
    @Composable
    override fun settingTitle(): String = "Setting"

    @Composable
    override fun settingThemeTitle(): String = "Theme"

    @Composable
    override fun settingVersion(): String = "Version: v0.1.0"

    @Composable
    override fun settingThemeLight(): String = "Light"

    @Composable
    override fun settingThemeDark(): String = "Dark"

    @Composable
    override fun settingThemeSync(): String = "Sync System"

    // Common
    @Composable
    override fun close(): String = "Close"

    @Composable
    override fun apply(): String = "Apply"
}

private interface StringList {
    // Home
    @Composable
    fun homeTitle(): String

    @Composable
    fun homeCreateFolder(): String

    @Composable
    fun homeAddBookmark(): String

    @Composable
    fun homeEdit(): String

    @Composable
    fun homeDelete(): String

    // Edit
    @Composable
    fun editTitle(name: String): String

    @Composable
    fun editNamePlaceHolder(): String

    @Composable
    fun editUrlPlaceHolder(): String

    // Directory
    @Composable
    fun directoryTitle(): String

    @Composable
    fun directoryNamePlaceHolder(): String

    // Delete
    @Composable
    fun deleteTitle(name: String): String

    @Composable
    fun deleteMessage(name: String): String

    // Bookmark
    @Composable
    fun bookmarkTitle(): String

    @Composable
    fun bookmarkNamePlaceHolder(): String

    @Composable
    fun bookmarkUrlPlaceHolder(): String

    // Setting
    @Composable
    fun settingTitle(): String

    @Composable
    fun settingThemeTitle(): String

    @Composable
    fun settingThemeLight(): String

    @Composable
    fun settingThemeDark(): String

    @Composable
    fun settingThemeSync(): String

    @Composable
    fun settingVersion(): String

    // Common
    @Composable
    fun close(): String

    @Composable
    fun apply(): String
}