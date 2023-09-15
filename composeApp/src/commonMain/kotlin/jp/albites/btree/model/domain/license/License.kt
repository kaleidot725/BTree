package jp.albites.btree.model.domain.license

data class License(
    val artifactId: String,
    val groupId: String,
    val name: String,
    val scm: Scm,
    val spdxLicenses: List<SpdxLicense>,
    val version: String
)