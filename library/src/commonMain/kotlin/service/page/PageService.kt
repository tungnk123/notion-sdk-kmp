package service.page

import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.page.CreatePageRequest
import core.data.model.internal.request.page.UpdatePageRequest
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