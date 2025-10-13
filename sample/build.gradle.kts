import java.util.Properties
import java.io.FileInputStream

plugins {
    application
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

group = "io.github.tungnk123"
version = "1.0.0"

repositories { mavenCentral() }

dependencies {
    implementation(project(":library"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.client.android)
    implementation(libs.bundles.ktor.common)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.status.pages)
}

application {
    mainClass.set("NotionClientFromOAuthKt")
}

kotlin { jvmToolchain(21) }

fun loadLocalProp(key: String): String? {
    val file = rootProject.file("local.properties")
    if (!file.exists()) return null
    val properties = Properties()
    FileInputStream(file).use(properties::load)
    return properties.getProperty(key)
}
fun readSecret(key: String): String? =
    loadLocalProp(key)
        ?: providers.gradleProperty(key).orNull
        ?: providers.environmentVariable(key).orNull

tasks.named<JavaExec>("run") {
    listOf(
        "NOTION_CLIENT_ID",
        "NOTION_CLIENT_SECRET",
        "NOTION_REDIRECT_URI"
    ).forEach { key ->
        readSecret(key)?.let { value ->
            environment(key, value)
            jvmArgs("-D$key=$value")
        }
    }
}