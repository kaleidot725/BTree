package jp.albites.btree.model.domain.license

import kotlinx.serialization.Serializable

@Serializable
data class SpdxLicense(
    val identifier: String,
    val name: String,
    val url: String,
)
