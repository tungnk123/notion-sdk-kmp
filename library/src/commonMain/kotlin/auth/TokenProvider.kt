package auth

interface TokenProvider { fun token(): String }

class StaticTokenProvider(private var value: String) : TokenProvider {
    override fun token(): String = value
    fun setToken(newValue: String) { value = newValue }
}
