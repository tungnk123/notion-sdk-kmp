package io.github.tungnk123.notionsdkkmp.service.page

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.CreatePageRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.UpdatePageRequest
import kotlinx.serialization.json.JsonObject

interface PageService {
    suspend fun create(request: CreatePageRequest): PageDto
    suspend fun retrieve(id: String): PageDto
    suspend fun update(id: String, request: UpdatePageRequest): PageDto
    suspend fun retrievePropertyItem(
        pageId: String,
        propertyId: String,
        startCursor: String? = null,
        pageSize: Int? = null
    ): JsonObject
}