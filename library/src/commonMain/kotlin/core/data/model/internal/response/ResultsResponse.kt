package core.data.model.internal.response

import core.data.model.internal.serializer.ResultsResponseTypedSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable(with = ResultsResponseTypedSerializer::class)
internal data class ResultsResponse<T : Any>(
    val results: List<T>,
    val nextCursor: String? = null,
    val hasMore: Boolean,
)