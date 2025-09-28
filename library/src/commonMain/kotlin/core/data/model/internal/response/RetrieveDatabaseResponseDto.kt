package core.data.model.internal.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RetrieveDatabaseResponseDto(
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val title: List<RetrieveDatabaseTitle>,
    val properties: Map<String, RetrieveDatabaseSchemaPropertyDto>
) {
    fun fullTitle(): String =
        title.joinToString("") { it.plainText }
}

@Serializable
internal data class RetrieveDatabaseTitle(
    @SerialName("plain_text")
    val plainText: String
)

@Serializable
internal sealed class RetrieveDatabaseSchemaPropertyDto {
    abstract val id: String
    abstract val name: String

    @Serializable
    @SerialName("title")
    data class Title(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("rich_text")
    data class Text(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("number")
    data class Number(
        override val id: String,
        override val name: String,
        val number: Wrapped,
    ) : RetrieveDatabaseSchemaPropertyDto() {
        @Serializable
        data class Wrapped(
            val format: Format
        )

        @Serializable
        enum class Format {
            @SerialName("number")
            Number,
            @SerialName("number_with_commas")
            NumberWithCommas,
            @SerialName("percent")
            Percent,
            @SerialName("dollar")
            Dollar,
            @SerialName("canadian_dollar")
            CanadianDollar,
            @SerialName("euro")
            Euro,
            @SerialName("pound")
            Pound,
            @SerialName("yen")
            Yen,
            @SerialName("ruble")
            Ruble,
            @SerialName("rupee")
            Rupee,
            @SerialName("won")
            Won,
            @SerialName("yuan")
            Yuan,
            @SerialName("real")
            Real,
            @SerialName("lira")
            Lira,
            @SerialName("rupiah")
            Rupiah,
            @SerialName("franc")
            Franc,
            @SerialName("hong_kong_dollar")
            HongKongDollar,
            @SerialName("new_zealand_dollar")
            NewZealandDollar,
            @SerialName("krona")
            Krona,
            @SerialName("norwegian_krone")
            NorwegianKrone,
            @SerialName("mexican_peso")
            MexicanPeso,
            @SerialName("rand")
            Rand,
            @SerialName("new_taiwan_dollar")
            NewTaiwanDollar,
            @SerialName("danish_krone")
            DanishKrone,
            @SerialName("zloty")
            Zloty,
            @SerialName("baht")
            Baht,
            @SerialName("forint")
            Forint,
            @SerialName("koruna")
            Koruna,
            @SerialName("shekel")
            Shekel,
            @SerialName("chilean_peso")
            ChileanPeso,
            @SerialName("philippine_peso")
            PhilippinePeso,
            @SerialName("dirham")
            Dirham,
            @SerialName("colombian_peso")
            ColombianPeso,
            @SerialName("riyal")
            Riyal,
            @SerialName("ringgit")
            Ringgit,
            @SerialName("leu")
            Leu
        }
    }

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        override val name: String,
        val select: Wrapped
    ) : RetrieveDatabaseSchemaPropertyDto() {
        @Serializable
        data class Wrapped(
            val options: List<Option>
        )

        @Serializable
        data class Option(
            val id: String,
            val name: String
        )
    }

    @Serializable
    @SerialName("multi_select")
    data class MultiSelect(
        override val id: String,
        override val name: String,
        @SerialName("multi_select")
        val multiSelect: Wrapped
    ) : RetrieveDatabaseSchemaPropertyDto() {
        @Serializable
        data class Wrapped(
            val options: List<Select.Option>
        )
    }

    @Serializable
    @SerialName("formula")
    data class Formula(
        override val id: String,
        override val name: String,
        val formula: Wrapped
    ) : RetrieveDatabaseSchemaPropertyDto() {
        @Serializable
        data class Wrapped(
            val expression: String
        )
    }

    @Serializable
    @SerialName("date")
    data class Date(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("people")
    data class People(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("files")
    data class Files(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("checkbox")
    data class Checkbox(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("url")
    data class Url(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("email")
    data class Email(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("phone_number")
    data class PhoneNumber(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("created_by")
    data class CreatedBy(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("last_edited_time")
    data class LastEditedTime(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("last_edited_by")
    data class LastEditedBy(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()

    @Serializable
    @SerialName("rollup")
    data class Rollup(override val id: String, override val name: String) : RetrieveDatabaseSchemaPropertyDto()
}