package io.github.tungnk123.notionsdkkmp.notion.model

import auth.model.OAuthTokenResponse
import auth.repository.AuthRepository
import io.github.tungnk123.notionsdkkmp.notion.NotionClient

data class NotionSession(
    val workspaceId: String,
    val workspaceName: String?,
    val token: OAuthTokenResponse,
    val client: NotionClient,
    val auth: AuthRepository
)

data class AuthIntent(val url: String, val state: String)

sealed class SessionState {
    data object SignedOut : SessionState()
    data class Authorized(val current: NotionSession, val all: List<NotionSessionMeta>) : SessionState()
    data class Authorizing(val intent: AuthIntent) : SessionState()
}

data class NotionSessionMeta(
    val workspaceId: String, val workspaceName: String?, val workspaceIcon: String?
)