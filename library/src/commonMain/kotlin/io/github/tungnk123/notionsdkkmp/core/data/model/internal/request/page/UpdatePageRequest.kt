package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.page

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.CoverDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePageRequest(
    val properties: Map<String, PagePropertyDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    val archived: Boolean? = null
)