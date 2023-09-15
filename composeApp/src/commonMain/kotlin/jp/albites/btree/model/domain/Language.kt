package jp.albites.btree.model.domain

const val ENGLISH_TEXT = "en-US"
const val JAPANESE_TEXT = "ja_JP"

enum class Language(val text: String) {
    ENGLISH(ENGLISH_TEXT),
    JAPANESE(JAPANESE_TEXT),
}
