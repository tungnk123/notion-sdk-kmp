package core.env

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Env {
    actual fun get(name: String): String? =
        try { System.getProperty(name) } catch (_: Throwable) { null }
            ?: try { System.getenv(name) } catch (_: Throwable) { null }
}