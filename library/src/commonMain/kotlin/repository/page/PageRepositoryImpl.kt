package repository.page

import core.data.mapper.toDomain
import core.data.model.internal.request.page.CreatePageRequest
import core.data.model.internal.request.page.UpdatePageRequest
import core.data.model.result.page.NotionPage
import kotlinx.serialization.json.JsonObject
import service.page.PageService

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