package core.env

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Env {
    actual fun get(name: String): String? = try {
        val proc = js("typeof process !== 'undefined' ? process : null")
        if (proc != null && proc.asDynamic().env != null) {
            proc.asDynamic().env[name] as? String
        } else null
    } catch (_: dynamic) { null }
}
