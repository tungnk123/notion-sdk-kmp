package core.data.model.internal.dto.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DataSourcePropertyType {
    @SerialName("checkbox") CHECKBOX,
    @SerialName("created_by") CREATED_BY,
    @SerialName("created_time") CREATED_TIME,
    @SerialName("date") DATE,
    @SerialName("email") EMAIL,
    @SerialName("files") FILES,
    @SerialName("formula") FORMULA,
    @SerialName("last_edited_by") LAST_EDITED_BY,
    @SerialName("last_edited_time") LAST_EDITED_TIME,
    @SerialName("multi_select") MULTI_SELECT,
    @SerialName("number") NUMBER,
    @SerialName("people") PEOPLE,
    @SerialName("phone_number") PHONE_NUMBER,
    @SerialName("relation") RELATION,
    @SerialName("rich_text") RICH_TEXT,
    @SerialName("rollup") ROLLUP,
    @SerialName("select") SELECT,
    @SerialName("status") STATUS,
    @SerialName("title") TITLE,
    @SerialName("url") URL,
    @SerialName("unique_id") UNIQUE_ID
}
