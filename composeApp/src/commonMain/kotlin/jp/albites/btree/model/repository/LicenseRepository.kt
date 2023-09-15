package jp.albites.btree.model.repository

import jp.albites.btree.model.domain.license.License
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource

@OptIn(ExperimentalResourceApi::class)
class LicenseRepository(val resource: Resource) {
    suspend fun get() : List<License> {
        return withContext(Dispatchers.IO) {
            val text = resource.readBytes().decodeToString()
            Json.decodeFromString<List<License>>(text)
        }
    }
}