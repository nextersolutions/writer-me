package io.writerme.core.models.dto

data class MetaTagsDto(
    val ogImage: String?, // still need nullability as its possible a site has none of these tags
    val ogTitle: String?,
    val ogUrl: String?,
    val ogDescription: String?,
    val twitterImage: String?,
    val twitterTitle: String?,
    val twitterDescription: String?,
    val twitterUrl: String?
)
