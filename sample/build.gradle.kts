import java.util.Properties

plugins {
    application
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

group = "io.github.tungnk123"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":library"))
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.client.android)
}

application {
    mainClass.set("SampleMainKt")
}

kotlin {
    jvmToolchain(21)
}

fun loadLocalProp(name: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) return null
    val properties = Properties()
    localPropertiesFile.inputStream().use { properties.load(it) }
    return properties.getProperty(name)
}

tasks.withType<JavaExec>().configureEach {
    fun getVal(key: String): String? =
        loadLocalProp(key)
            ?: (project.providers.gradleProperty(key).orNull)
            ?: (project.providers.environmentVariable(key).orNull)

    listOf(
        "NOTION_TOKEN",
        "NOTION_TEST_PAGE_ID",
        "NOTION_TEST_DATABASE_ID",
        "NOTION_TEST_DATASOURCE_ID",
        "NOTION_PARENT_DATABASE_ID",
        "NOTION_TEST_BLOCK_ID"
    ).forEach { k ->
        getVal(k)?.let {
            environment(k, it)
            systemProperty(k, it)
        }
    }
}