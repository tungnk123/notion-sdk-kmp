package core.data.model.result.user

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
        val email: String? = null
    ) : NotionUser()

    @Serializable
    @SerialName("bot")
    data class Bot(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        val owner: Owner? = null,
        @SerialName("workspace_name")
        val workspaceName: String? = null,
        val workspaceLimits: WorkspaceLimits? = null
    ) : NotionUser() {
        @Serializable
        sealed class Owner {
            @Serializable
            @SerialName("workspace")
            data class Workspace(val workspace: Boolean = true) : Owner()
            @Serializable
            @SerialName("user")
            data class User(val id: String) : Owner()
        }
        @Serializable
        data class WorkspaceLimits(
            @SerialName("max_file_upload_size_in_bytes")
            val maxFileUploadSizeInBytes: Long? = null
        )
    }
}