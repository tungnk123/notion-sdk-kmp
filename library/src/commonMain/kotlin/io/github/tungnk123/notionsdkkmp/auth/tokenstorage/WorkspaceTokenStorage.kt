package io.github.tungnk123.notionsdkkmp.auth.tokenstorage

import io.github.tungnk123.notionsdkkmp.auth.model.OAuthTokenResponse

class WorkspaceTokenStorage(
    private val multi: MultiTokenStorage, private val workspaceId: String
) : TokenStorage {
    override fun get(): OAuthTokenResponse? = multi.get(workspaceId)
    override fun set(token: OAuthTokenResponse) {
        multi.set(workspaceId, token, makeCurrent = true)
    }

    override fun clear() {
        multi.remove(workspaceId)
    }
}