package core.data.model.internal.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class UpdateDataSourceRequest(
    val name: String? = null,
    val properties: JsonObject? = null
)
