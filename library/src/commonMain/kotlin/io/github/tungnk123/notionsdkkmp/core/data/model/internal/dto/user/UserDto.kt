package io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class UserDto {
    @SerialName("object")
    abstract val objectType: String
    abstract val id: String
    abstract val name: String?
    @SerialName("avatar_url")
    abstract val avatarUrl: String?

    @Serializable
    @SerialName("person")
    data class Person(
        @SerialName("object")
        override val objectType: String = "user",
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        val person: Value
    ) : UserDto() {
        @Serializable
        data class Value(val email: String? = null)
    }

    @Serializable
    @SerialName("bot")
    data class Bot(
        @SerialName("object")
        override val objectType: String = "user",
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        val bot: BotValue? = null
    ) : UserDto() {
        @Serializable
        data class BotValue(
            val owner: Owner? = null,
            @SerialName("workspace_name")
            val workspaceName: String? = null,
            @SerialName("workspace_limits")
            val workspaceLimits: WorkspaceLimits? = null
        )
        @Serializable
        data class Owner(
            val type: String,
            val user: MinimalUser? = null,
            val workspace: Boolean? = null
        )
        @Serializable
        data class MinimalUser(val id: String)
        @Serializable
        data class WorkspaceLimits(
            @SerialName("max_file_upload_size_in_bytes")
            val maxFileUploadSizeInBytes: Long? = null
        )
    }
}

@Serializable
data class PartialUserDto(
    @SerialName("object") val objectType: String = "user",
    val id: String
)