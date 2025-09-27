package core.data.model.result

import core.data.model.serializer.NotionResultsTypedSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = NotionResultsTypedSerializer::class)
data class NotionResults<T>(
    val results: List<T>,

    @SerialName("next_cursor")
    val nextCursor: String? = null,
    @SerialName("has_more")
    val hasMore: Boolean,
)

@Serializable
data class NotionDatabaseRow(
    val id: String,
    val icon: NotionIcon? = null,
    val columns: Map<String, NotionDatabaseColumn>,
)

@Serializable
data class NotionDatabaseColumn(
    val key: String,
    val value: NotionDatabaseProperty,
)

@Serializable
sealed class NotionDatabaseProperty {
    abstract val id: String

    @Serializable
    @SerialName("title")
    data class Title(override val id: String, val text: String) : NotionDatabaseProperty()

    @Serializable
    @SerialName("rich_text")
    data class Text(override val id: String, val text: String, val parts: List<Part>) :
        NotionDatabaseProperty() {
        @Serializable
        data class Part(val text: String, val url: String?)
    }

    @Serializable
    @SerialName("number")
    data class Number(override val id: String, val number: Double?) : NotionDatabaseProperty()

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        val selected: Option?,
    ) : NotionDatabaseProperty() {
        @Serializable
        data class Option(
            val id: String,
            val name: String,
        )
    }

    @Serializable
    @SerialName("multi_select")
    data class MultiSelect(
        override val id: String,
        val selected: List<Select.Option>,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("date")
    data class Date(override val id: String, val start: String?, val end: String?) : NotionDatabaseProperty()

    @Serializable
    @SerialName("people")
    data class People(override val id: String, val people: List<Person>) : NotionDatabaseProperty() {
        @Serializable
        sealed class Person {
            @Serializable
            @SerialName("user")
            data class User(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String?,
                val email: String,
            ) : Person()

            @Serializable
            @SerialName("user")
            data class Bot(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
            ) : Person()
        }
    }

    @Serializable
    @SerialName("files")
    data class Files(override val id: String, val files: List<Item>) : NotionDatabaseProperty() {
        @Serializable
        data class Item(val url: String, val name: String?, val expiryTime: String?, val type: Type) {
            @Serializable
            enum class Type {
                @SerialName("external")
                External,

                @SerialName("file")
                File;
            }
        }
    }

    @Serializable
    @SerialName("checkbox")
    data class Checkbox(override val id: String, val selected: Boolean) : NotionDatabaseProperty()

    @Serializable
    @SerialName("url")
    data class Url(override val id: String, val url: String? = null) : NotionDatabaseProperty()

    @Serializable
    @SerialName("email")
    data class Email(override val id: String, val email: String? = null) : NotionDatabaseProperty()

    @Serializable
    @SerialName("phone_number")
    data class PhoneNumber(
        override val id: String,
        @SerialName("phone_number")
        val phoneNumber: String? = null,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("formula")
    data class Formula(
        override val id: String, val formula: Item,
    ) : NotionDatabaseProperty() {
        @Serializable
        sealed class Item {
            @Serializable
            @SerialName("string")
            data class Str(val string: String) : Item()

            @Serializable
            @SerialName("number")
            data class Number(val number: Double) : Item()

            @Serializable
            @SerialName("boolean")
            data class Bool(val boolean: Boolean) : Item()

            @Serializable
            @SerialName("date")
            data class Date(val date: String) : Item()
        }
    }

    // todo: empty because no samples provided, only the description which is not reliable
    @Serializable
    @SerialName("relation")
    data class Relation(override val id: String) : NotionDatabaseProperty()

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(
        override val id: String,
        @SerialName("created_time")
        val createdTime: String,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("last_edited_time")
    data class LastEditedTime(
        override val id: String,
        @SerialName("last_edited_time")
        val lastEditedTime: String,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("created_by")
    data class CreatedBy(
        override val id: String,
        @SerialName("created_by")
        val createdBy: People.Person,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("last_edited_by")
    data class LastEditedBy(
        override val id: String,
        @SerialName("last_edited_by")
        val lastEditedBy: People.Person,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("rollup")
    data class Rollup(
        override val id: String,
    ) : NotionDatabaseProperty()
}