package service.page

import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.page.CreatePageRequest
import core.data.model.internal.request.page.UpdatePageRequest
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonObject

private object Routes {
    private const val PAGES = "pages"
    fun create() = PAGES
    fun retrieve(id: String) = "$PAGES/$id"
    fun update(id: String) = "$PAGES/$id"
    fun property(pageId: String, propertyId: String) = "$PAGES/$pageId/properties/$propertyId"
}

class PageServiceImpl(
    private val http: NotionHttp
) : PageService {

    override suspend fun create(request: CreatePageRequest): PageDto = http.post(Routes.create()) {
        setBody(request)
    }

    override suspend fun retrieve(id: String): PageDto = http.get(Routes.retrieve(id))

    override suspend fun update(id: String, request: UpdatePageRequest): PageDto =
        http.patch(Routes.update(id)) { setBody(request) }

    override suspend fun retrievePropertyItem(
        pageId: String, propertyId: String, startCursor: String?, pageSize: Int?
    ): JsonObject = http.get(Routes.property(pageId, propertyId)) {
        if (startCursor != null) url.parameters.append("start_cursor", startCursor)
        if (pageSize != null) url.parameters.append("page_size", pageSize.toString())
    }
}