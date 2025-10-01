package core.data.model.internal.request.page

import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePageRequest(
    val parent: ParentDto,
    val properties: Map<String, PagePropertyDto>,
    val icon: Icon? = null,
    val cover: Cover? = null
) {
    @Serializable
    data class Icon(
        val type: String,
        val emoji: String? = null
    )

    @Serializable
    data class Cover(
        val type: String,
        val external: External? = null
    ) {
        @Serializable
        data class External(
            val url: String
        )
    }
}

