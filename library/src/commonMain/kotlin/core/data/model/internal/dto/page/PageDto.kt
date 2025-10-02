package core.data.model.internal.dto.page

import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.common.IconDto
import core.data.model.internal.dto.user.PartialUserDto
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
    val inTrash: Boolean = false,
    val properties: Map<String, PagePropertyDto>,
    val url: String,
    @SerialName("public_url")
    val publicUrl: String? = null,
)