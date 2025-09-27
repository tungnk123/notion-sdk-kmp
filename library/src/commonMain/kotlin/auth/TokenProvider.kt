package auth

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