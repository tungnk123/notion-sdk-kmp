package core.data.model.result.datasource

import kotlinx.serialization.Serializable

@Serializable
sealed class NotionDataSourceProperty {
    abstract val id: String
    abstract val name: String?
    abstract val description: String?

    @Serializable data class Title(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class RichText(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class Checkbox(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class Date(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class Email(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class Files(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class People(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class Url(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class PhoneNumber(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()

    @Serializable
    data class Number(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val format: String?
    ) : NotionDataSourceProperty()

    @Serializable
    data class Select(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val options: List<Option>
    ) : NotionDataSourceProperty()

    @Serializable
    data class MultiSelect(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val options: List<Option>
    ) : NotionDataSourceProperty()

    @Serializable
    data class Status(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val options: List<Option>,
        val groups: List<StatusGroup>
    ) : NotionDataSourceProperty()

    @Serializable
    data class Relation(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val targetDataSourceId: String?,
        val syncedPropertyId: String?,
        val syncedPropertyName: String?
    ) : NotionDataSourceProperty()

    @Serializable
    data class Rollup(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val function: String?,
        val relationPropertyId: String?,
        val relationPropertyName: String?,
        val rollupPropertyId: String?,
        val rollupPropertyName: String?
    ) : NotionDataSourceProperty()

    @Serializable
    data class Formula(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val expression: String?
    ) : NotionDataSourceProperty()

    @Serializable data class CreatedBy(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class CreatedTime(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class LastEditedBy(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()
    @Serializable data class LastEditedTime(override val id: String, override val name: String?, override val description: String?) : NotionDataSourceProperty()

    @Serializable
    data class UniqueId(
        override val id: String,
        override val name: String?,
        override val description: String?,
        val prefix: String?
    ) : NotionDataSourceProperty()

    @Serializable
    data class Option(
        val id: String,
        val name: String,
        val color: String? = null
    )

    @Serializable
    data class StatusGroup(
        val id: String,
        val name: String,
        val color: String? = null,
        val optionIds: List<String> = emptyList()
    )
}
