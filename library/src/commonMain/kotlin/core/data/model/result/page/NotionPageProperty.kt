package core.data.model.result.page

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionPageProperty {
    abstract val id: String

    @Serializable
    @SerialName("title")
    data class Title(
        override val id: String,
        val title: List<Value>,
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            val type: String,
            val text: Text,
            val annotations: Annotations,
            @SerialName("plain_text") val plainText: String,
            val href: String? = null
        ) {
            @Serializable
            data class Text(
                val content: String,
                val link: String? = null
            )

            @Serializable
            data class Annotations(
                val bold: Boolean,
                val italic: Boolean,
                val strikethrough: Boolean,
                val underline: Boolean,
                val code: Boolean,
                val color: String
            )
        }

        fun plainText(): String =
            title.joinToString("") { it.plainText }
    }

    @Serializable
    @SerialName("rich_text")
    data class Text(
        override val id: String,
        @SerialName("rich_text")
        val richText: List<Value>,
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            @SerialName("plain_text")
            val plainText: String,
            val href: String?,
        )

        fun plainText(): String =
            richText.joinToString("") { it.plainText }
    }

    @Serializable
    @SerialName("number")
    data class Number(
        override val id: String,
        val number: Double? = null,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        val select: Value? = null,
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            val id: String,
            val name: String,
            val color: String,
        )
    }

    @Serializable
    @SerialName("multi_select")
    data class MultiSelect(
        override val id: String,
        @SerialName("multi_select")
        val multiSelect: List<Select.Value>,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("date")
    data class Date(
        override val id: String,
        val date: Value? = null,
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            val start: String,
            val end: String? = null,
        )
    }

    @Serializable
    @SerialName("people")
    data class People(
        override val id: String,
        val people: List<Value>,
    ) : NotionPageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("person")
            data class Person(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
                val person: User,
            ) : Value() {
                @Serializable
                data class User(
                    val email: String,
                )
            }

            @Serializable
            @SerialName("bot")
            data class Bot(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
            ) : Value()
        }
    }

    @Serializable
    @SerialName("files")
    data class Files(
        override val id: String,
        val files: List<Value>,
    ) : NotionPageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("external")
            data class External(
                val url: String,
            ) : Value()

            @Serializable
            @SerialName("file")
            data class File(
                val name: String,
                val file: Item,
            ) : Value() {
                @Serializable
                data class Item(
                    val url: String,
                    @SerialName("expiry_time")
                    val expiryTime: String,
                )
            }
        }
    }

    @Serializable
    @SerialName("checkbox")
    data class Checkbox(
        override val id: String,
        val checkbox: Boolean,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("url")
    data class Url(
        override val id: String,
        val url: String? = null,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("email")
    data class Email(
        override val id: String,
        val email: String? = null,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("phone_number")
    data class PhoneNumber(
        override val id: String,
        @SerialName("phone_number")
        val phoneNumber: String? = null,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("formula")
    data class Formula(
        override val id: String,
        val formula: Value,
    ) : NotionPageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("string")
            data class Str(val string: String) : Value()

            @Serializable
            @SerialName("number")
            data class Number(val number: Double) : Value()

            @Serializable
            @SerialName("boolean")
            data class Bool(val boolean: Boolean) : Value()

            @Serializable
            @SerialName("date")
            data class Date(val date: String) : Value()
        }
    }

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(
        override val id: String,
        @SerialName("created_time")
        val createdTime: String,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("last_edited_time")
    data class LastEditedTime(
        override val id: String,
        @SerialName("last_edited_time")
        val lastEditedTime: String,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("created_by")
    data class CreatedBy(
        override val id: String,
        @SerialName("created_by")
        val createdBy: People.Value.Person,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("last_edited_by")
    data class LastEditedBy(
        override val id: String,
        @SerialName("last_edited_by")
        val lastEditedBy: People.Value.Person,
    ) : NotionPageProperty()

    @Serializable
    @SerialName("rollup")
    data class Rollup(override val id: String) : NotionPageProperty()

    @Serializable
    @SerialName("status")
    data class Status(
        override val id: String,
        val status: StatusValue
    ) : NotionPageProperty()

    @Serializable
    data class StatusValue(
        val id: String? = null,
        val name: String? = null,
        val color: String? = null
    )

    @Serializable
    @SerialName("relation")
    data class Relation(
        override val id: String,
        val relation: List<Ref> = emptyList(),
        @SerialName("has_more") val hasMore: Boolean? = null
    ) : NotionPageProperty() {
        @Serializable
        data class Ref(val id: String)
    }

    @Serializable
    @SerialName("unique_id")
    data class UniqueId(
        override val id: String,
        @SerialName("unique_id") val uniqueId: Value
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            val number: Long? = null,
            val prefix: String? = null
        )
    }

    @Serializable
    @SerialName("verification")
    data class Verification(
        override val id: String,
        val verification: Value
    ) : NotionPageProperty() {
        @Serializable
        data class Value(
            val state: String,
            @SerialName("verified_by") val verifiedBy: VerifiedBy? = null,
            val date: DateRange? = null
        )

        @Serializable
        data class VerifiedBy(
            @SerialName("object") val objectType: String = "user",
            val id: String,
            val name: String? = null,
            @SerialName("avatar_url") val avatarUrl: String? = null,
            val type: String? = null
        )

        @Serializable
        data class DateRange(
            val start: String? = null,
            val end: String? = null,
            @SerialName("time_zone") val timeZone: String? = null
        )
    }
}