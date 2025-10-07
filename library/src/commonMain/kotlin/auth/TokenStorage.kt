package auth

import auth.oauth.OAuthTokenResponse

interface TokenStorage {
    fun get(): OAuthTokenResponse?
    fun set(token: OAuthTokenResponse)
    fun clear()
}

class InMemoryTokenStorage : TokenStorage {
    private var authTokenResponse: OAuthTokenResponse? = null
    override fun get(): OAuthTokenResponse? = authTokenResponse
    override fun set(token: OAuthTokenResponse) {
        authTokenResponse = token
    }

    override fun clear() {
        authTokenResponse = null
    }
}
