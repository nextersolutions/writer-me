package io.writerme.core.contracts.utils

import io.writerme.core.models.dto.MetaTagsDto

interface MetaTagScraper {
    suspend fun scrape(givenUrl: String): MetaTagsDto
}