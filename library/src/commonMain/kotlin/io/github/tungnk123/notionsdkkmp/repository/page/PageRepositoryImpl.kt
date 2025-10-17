package io.github.tungnk123.notionsdkkmp.repository.page

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.CreatePageRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page.UpdatePageRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage
import kotlinx.serialization.json.JsonObject
import io.github.tungnk123.notionsdkkmp.service.page.PageService

class PageRepositoryImpl(
    private val service: PageService
) : PageRepository {

    override suspend fun create(req: CreatePageRequest): NotionPage =
        service.create(req).toDomain()

    override suspend fun retrieve(id: String): NotionPage =
        service.retrieve(id).toDomain()

    override suspend fun update(id: String, req: UpdatePageRequest): NotionPage =
        service.update(id, req).toDomain()

    override suspend fun retrievePropertyItem(
        pageId: String,
        propertyId: String,
        startCursor: String?,
        pageSize: Int?
    ): JsonObject = service.retrievePropertyItem(pageId, propertyId, startCursor, pageSize)
}