package jp.albites.btree.util

import java.util.UUID

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}