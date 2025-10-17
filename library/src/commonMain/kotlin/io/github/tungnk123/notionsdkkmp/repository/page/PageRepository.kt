package io.github.tungnk123.notionsdkkmp.repository.page

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.CreatePageRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.UpdatePageRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage
import kotlinx.serialization.json.JsonObject

interface PageRepository {
    suspend fun create(req: CreatePageRequest): NotionPage
    suspend fun retrieve(id: String): NotionPage
    suspend fun update(id: String, req: UpdatePageRequest): NotionPage
    suspend fun retrievePropertyItem(
        pageId: String, propertyId: String, startCursor: String? = null, pageSize: Int? = null
    ): JsonObject
}