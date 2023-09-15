package jp.albites.btree.model.domain.license

import kotlinx.serialization.Serializable

@Serializable
data class License(
    val artifactId: String,
    val groupId: String,
    val name: String,
    val scm: Scm,
    val spdxLicenses: List<SpdxLicense>,
    val version: String
)
