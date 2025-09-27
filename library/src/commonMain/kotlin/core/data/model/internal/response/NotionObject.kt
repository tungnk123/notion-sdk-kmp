package core.data.model.internal.response

import core.data.model.internal.obj.IconObject
import kotlinx.serialization.Serializable

@Serializable
internal data class PageObject(
    val id: String,
    val url: String,
    val properties: Map<String, PageProperty>,
    val iconObject: IconObject? = null,
)