package core.data.model.internal.request.page

import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePageRequest(
    val properties: Map<String, PagePropertyDto>? = null,
    val icon: CreatePageRequest.Icon? = null,
    val cover: CreatePageRequest.Cover? = null,
    @SerialName("archived") val archived: Boolean? = null
)
