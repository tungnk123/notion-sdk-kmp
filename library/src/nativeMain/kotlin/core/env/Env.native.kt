package core.env

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.getenv
import kotlinx.cinterop.toKString

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Env {
    @OptIn(ExperimentalForeignApi::class)
    actual fun get(name: String): String? = getenv(name)?.toKString()
}
