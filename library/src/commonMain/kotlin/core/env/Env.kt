@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package core.env

expect object Env {
    fun get(name: String): String?
}