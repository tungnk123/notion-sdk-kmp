package auth

import auth.repository.AuthRepository

interface TokenProvider {
    fun token(): String
}

interface MutableTokenProvider : TokenProvider {
    fun setToken(value: String)
}

class StaticTokenProvider(private var value: String) : MutableTokenProvider {
    override fun token(): String = value
    override fun setToken(value: String) { this.value = value }
}

class OAuthTokenProvider(
    private val repo: AuthRepository
) : TokenProvider {
    override fun token(): String = repo.accessTokenOrNull().orEmpty()
}