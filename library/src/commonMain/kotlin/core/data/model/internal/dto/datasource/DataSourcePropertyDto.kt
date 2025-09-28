package core.data.model.internal.dto.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSourcePropertyDto(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val type: DataSourcePropertyType,
    val checkbox: EmptyObj? = null,
    @SerialName("created_by") val createdBy: EmptyObj? = null,
    @SerialName("created_time") val createdTime: EmptyObj? = null,
    val date: EmptyObj? = null,
    val email: EmptyObj? = null,
    val files: EmptyObj? = null,
    val formula: FormulaConfig? = null,
    @SerialName("last_edited_by") val lastEditedBy: EmptyObj? = null,
    @SerialName("last_edited_time") val lastEditedTime: EmptyObj? = null,
    @SerialName("multi_select") val multiSelect: SelectConfig? = null,
    val number: NumberConfig? = null,
    val people: EmptyObj? = null,
    @SerialName("phone_number") val phoneNumber: EmptyObj? = null,
    val relation: RelationConfig? = null,
    @SerialName("rich_text") val richText: EmptyObj? = null,
    val rollup: RollupConfig? = null,
    val select: SelectConfig? = null,
    val status: StatusConfig? = null,
    val title: EmptyObj? = null,
    val url: EmptyObj? = null,
    @SerialName("unique_id") val uniqueId: UniqueIdConfig? = null
)

@Serializable
data object EmptyObj

@Serializable
data class NumberConfig(val format: String? = null)

@Serializable
data class SelectConfig(val options: List<SelectOption> = emptyList())

@Serializable
data class SelectOption(val id: String, val name: String, val color: String? = null)

@Serializable
data class StatusConfig(
    val options: List<StatusOption> = emptyList(),
    val groups: List<StatusGroup> = emptyList()
)

@Serializable
data class StatusOption(val id: String, val name: String, val color: String? = null)

@Serializable
data class StatusGroup(
    val id: String,
    val name: String,
    val color: String? = null,
    @SerialName("option_ids") val optionIds: List<String> = emptyList()
)

@Serializable
data class FormulaConfig(val expression: String)

@Serializable
data class RelationConfig(
    @SerialName("data_source_id") val dataSourceId: String? = null,
    @SerialName("database_id") val databaseIdLegacy: String? = null,
    @SerialName("synced_property_id") val syncedPropertyId: String? = null,
    @SerialName("synced_property_name") val syncedPropertyName: String? = null,
    @SerialName("dual_property") val dualProperty: RelationDual? = null
)

@Serializable
data class RelationDual(
    @SerialName("synced_property_id") val syncedPropertyId: String? = null,
    @SerialName("synced_property_name") val syncedPropertyName: String? = null
)

@Serializable
data class RollupConfig(
    val function: String? = null,
    @SerialName("relation_property_id") val relationPropertyId: String? = null,
    @SerialName("relation_property_name") val relationPropertyName: String? = null,
    @SerialName("rollup_property_id") val rollupPropertyId: String? = null,
    @SerialName("rollup_property_name") val rollupPropertyName: String? = null
)

@Serializable
data class UniqueIdConfig(val prefix: String? = null)
