package jp.albites.btree.view.resources

import cafe.adriel.lyricist.LyricistStrings
import jp.albites.btree.model.domain.ENGLISH_TEXT
import jp.albites.btree.model.domain.JAPANESE_TEXT

data class Strings(
    val homeTitle: String = "BTree",

    val homeCreateFolder: String = "Create Folder",

    val homeAddBookmark: String = "Add Bookmark",

    val homeEdit: String = "Edit",

    val homeDelete: String = "Delete",

    val editTitle: String = "Edit",

    val editNamePlaceHolder: String = "Name",

    val editUrlPlaceHolder: String = "URL",

    val directoryTitle: String = "Create Folder",

    val directoryNamePlaceHolder: String = "Name",

    val deleteTitle: String = "Delete",

    val deleteMessage: (name: String) -> String = { "Do you delete $it?" },

    val bookmarkTitle: String = "Create Link",

    val bookmarkNamePlaceHolder: String = "Name",

    val bookmarkUrlPlaceHolder: String = "URL",

    val settingTitle: String = "Setting",

    val settingThemeTitle: String = "Theme",

    val settingLanguageTitle: String = "Language",

    val settingLanguageEnglish: String = "English",

    val settingLanguageJapanese: String = "日本語",

    val settingVersion: String = "Version: v0.1.0",

    val settingThemeLight: String = "Light",

    val settingThemeDark: String = "Dark",

    val settingThemeSync: String = "Sync System",

    val settingLicenseTitle: String = "License",

    val close: String = "Close",

    val apply: String = "Apply",
)

@LyricistStrings(languageTag = ENGLISH_TEXT, default = true)
val EnStrings = Strings(
    homeTitle = "BTree",

    homeCreateFolder = "Create Folder",

    homeAddBookmark = "Add Bookmark",

    homeEdit = "Edit",

    homeDelete = "Delete",

    editTitle = "Edit",

    editNamePlaceHolder = "Name",

    editUrlPlaceHolder = "URL",

    directoryTitle = "Create Folder",

    directoryNamePlaceHolder = "Name",

    deleteTitle = "Delete",

    deleteMessage = { "Do you delete $it?" },

    bookmarkTitle = "Create Link",

    bookmarkNamePlaceHolder = "Name",

    bookmarkUrlPlaceHolder = "URL",

    settingTitle = "Setting",

    settingThemeTitle = "Theme",

    settingLanguageTitle = "Language",

    settingVersion = "Version: v0.1.0",

    settingThemeLight = "Light",

    settingThemeDark = "Dark",

    settingThemeSync = "Sync System",

    settingLicenseTitle = "License",

    close = "Close",

    apply = "Apply",
)

@LyricistStrings(languageTag = JAPANESE_TEXT, default = false)
val JaStrings = Strings(
    homeTitle = "BTree",

    homeCreateFolder = "フォルダを作成する",

    homeAddBookmark = "ブックマークを作成する",

    homeEdit = "編集",

    homeDelete = "削除",

    editTitle = "編集",

    editNamePlaceHolder = "名前",

    editUrlPlaceHolder = "URL",

    directoryTitle = "フォルダ作成",

    directoryNamePlaceHolder = "名前",

    deleteTitle = "削除",

    deleteMessage = { "${it}を削除しますか?" },

    bookmarkTitle = "ブックマーク作成",

    bookmarkNamePlaceHolder = "名前",

    bookmarkUrlPlaceHolder = "URL",

    settingTitle = "設定",

    settingThemeTitle = "テーマ",

    settingLanguageTitle = "言語",

    settingVersion = "バージョン: v0.1.0",

    settingThemeLight = "明るい",

    settingThemeDark = "暗い",

    settingThemeSync = "システム同期",

    settingLicenseTitle = "ライセンス情報",

    close = "閉じる",

    apply = "適用する",
)
