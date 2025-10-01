package core.data.model.internal.dto.page

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.PartialUserDto
import core.data.model.internal.dto.datasource.ParentDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDto(
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    @SerialName("created_by")
    val createdBy: PartialUserDto,
    @SerialName("last_edited_by")
    val lastEditedBy: PartialUserDto,
    val cover: IconDto? = null,
    val icon: IconDto? = null,
    val parent: ParentDto,
    val archived: Boolean,
    @SerialName("in_trash")
    val inTrash: Boolean,
    val properties: Map<String, PagePropertyDto>,
    val url: String,
    @SerialName("public_url")
    val publicUrl: String? = null,
)