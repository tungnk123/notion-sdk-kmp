package core.data.model.internal.request.database

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.datasource.CoverDto
import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDatabaseRequest(
    val parent: ParentDto? = null,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    @SerialName("is_inline") val isInline: Boolean? = null,
    val archived: Boolean? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null
)
