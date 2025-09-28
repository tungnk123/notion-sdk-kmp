package core.data.model.internal.response

import core.data.model.internal.serializer.ResultsResponseTypedSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ResultsResponseTypedSerializer::class)
data class ResultsResponseDto<T : Any>(
    val results: List<T>,
    val nextCursor: String? = null,
    val hasMore: Boolean,
)