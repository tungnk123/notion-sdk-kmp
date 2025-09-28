import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import java.util.*

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.tungnk123"
version = "1.0.0"

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
    }
    js(IR) { browser() }
    iosX64(); iosArm64(); iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.bundles.ktor.common)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.atomicfu)
            }
        }
        val jvmMain by getting { dependencies { implementation(libs.ktor.client.cio) } }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.koin.android)
            }
        }
        val jsMain by getting { dependencies { implementation(libs.ktor.client.js) } }
        val iosMain by getting { dependencies { implementation(libs.ktor.client.darwin) } }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.ktor.client.mock)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

android {
    namespace = "io.github.tungnk123.notion.sdk.kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "notion-sdk-kmp", version.toString())

    pom {
        name = "notion-sdk-kmp"
        description = "Kotlin Multiplatform Notion SDK"
        inceptionYear = "2024"
        url = "https://github.com/tungnk123/notion-sdk-kmp"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers { developer { id = "tungnk123"; name = "Tùng Đoàn"; url = "https://github.com/tungnk123" } }
        scm {
            url = "https://github.com/tungnk123/notion-sdk-kmp"
            connection = "scm:git:https://github.com/tungnk123/notion-sdk-kmp.git"
            developerConnection = "scm:git:ssh://git@github.com/tungnk123/notion-sdk-kmp.git"
        }
    }
}

fun loadLocalProp(name: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) return null
    val properties = Properties()
    localPropertiesFile.inputStream().use { properties.load(it) }
    return properties.getProperty(name)
}

val notionToken: String? =
    loadLocalProp("NOTION_TOKEN")
        ?: (providers.gradleProperty("NOTION_TOKEN").orNull)
        ?: (providers.environmentVariable("NOTION_TOKEN").orNull)

tasks.withType<Test>().configureEach {
    notionToken?.let {
        environment("NOTION_TOKEN", it)
        systemProperty("NOTION_TOKEN", it)
    }
}

tasks.withType<KotlinNativeTest>().configureEach {
    notionToken?.let { environment("NOTION_TOKEN", it) }
}

tasks.withType<KotlinJsTest>().configureEach {
    notionToken?.let { environment("NOTION_TOKEN", it) }
}