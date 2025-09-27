package core.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionUser {
    abstract val id: String
    abstract val name: String?

    @SerialName("avatar_url")
    abstract val avatarUrl: String?

    @Serializable
    @SerialName("person")
    data class Person(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,

        val email: String,
    ) : NotionUser()

    @Serializable
    @SerialName("bot")
    data class Bot(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
    ) : NotionUser()
}