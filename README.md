# notion-sdk-kmp

[![Maven Central](https://img.shields.io/maven-central/v/io.github.tungnk123/notion-sdk-kmp)](https://central.sonatype.com/artifact/io.github.tungnk123/notion-sdk-kmp)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin)](https://kotlinlang.org)
[![KMP](https://img.shields.io/badge/Kotlin_Multiplatform-Android%20%7C%20iOS%20%7C%20JVM%20%7C%20JS%20%7C%20macOS%20%7C%20Linux-brightgreen)](https://kotlinlang.org/docs/multiplatform.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A **Kotlin Multiplatform** SDK for the [Notion API](https://developers.notion.com/), published on Maven Central. Write your Notion integration once and run it on Android, iOS, JVM, JavaScript, macOS, and Linux — no code duplication, no platform-specific workarounds.

---

## Supported Platforms

| Platform | Target |
|---|---|
| Android | `androidTarget` |
| iOS | `iosX64`, `iosArm64`, `iosSimulatorArm64` |
| JVM (Desktop / Server) | `jvm` |
| JavaScript (Browser) | `js` |
| macOS | `macosX64`, `macosArm64` |
| Linux | `linuxX64` |

---

## Architecture

The library is structured in three layers, all written in `commonMain`:

```
NotionClient
    ├── Repository  (public API — suspend functions, clean domain types)
    │       └── Service  (HTTP communication via Ktor)
    │               └── NotionHttp  (auth injection, base URL, headers)
    └── DI modules (Koin — optional, wired automatically)
```

- **Repository layer** exposes idiomatic Kotlin interfaces (`PageRepository`, `BlockRepository`, …).
- **Service layer** holds Ktor HTTP calls and `@Serializable` DTOs.
- **NotionHttp** injects the `Authorization` header via a pluggable `TokenProvider`, keeping auth decoupled from transport.
- **Platform engines** are injected per-target (`CIO` on JVM, `Android` on Android, `Darwin` on Apple, `Js` on JS), so the common code never touches platform internals.

---

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation("io.github.tungnk123:notion-sdk-kmp:1.1.11")
}
```

---

## Quick Start

### Internal Integration (API Token)

```kotlin
val client = NotionClientFactory.fromToken(token = "secret_...")

// Retrieve a page
val page = client.pageRepository.retrieve(pageId)

// Query a database
val results = client.databaseRepository.query(
    id = databaseId,
    req = QueryDatabaseRequest(/* filters, sorts … */)
)

// Full-text search
val pages = client.searchRepository.searchPages(query = "Books")
```

### Custom Token Provider

Useful when the token is stored in your own secure storage or refreshed dynamically:

```kotlin
class MyTokenProvider : TokenProvider {
    override fun token(): String = MySecureStorage.read("notion_token")
}

val client = NotionClientFactory.fromTokenProvider(MyTokenProvider())
```

### OAuth 2.0 (Public Integration)

```kotlin
val repo = AuthRepository(
    service     = AuthServiceImpl(httpClient),
    clientId    = "...",
    clientSecret = "...",
    storage     = InMemoryTokenStorage()
)

// 1. Redirect the user
val authorizeUrl = repo.authorizeUrl(redirectUri, state, ownerWorkspace = false)

// 2. Handle the callback
val token = repo.exchangeCodeForTokenAndSaveToken(code, redirectUri)

// 3. Build the client
val client = NotionClientFactory.fromAuthRepository(repo)
```

### Multi-Workspace Session Management

```kotlin
val manager = NotionSessionManager(authFactory, tokenStore)

// Observe auth state reactively
manager.state.collect { state ->
    when (state) {
        is SessionState.SignedOut   -> showLoginScreen()
        is SessionState.Authorizing -> openBrowser(state.intent.url)
        is SessionState.Authorized  -> showHome(state.session)
    }
}

// Switch between workspaces
manager.switchWorkspace(workspaceId)

// Sign out
manager.disconnectWorkspace(workspaceId)
```

---

## API Coverage

| Resource | Operations |
|---|---|
| **Page** | `create`, `retrieve`, `update`, `retrievePropertyItem` |
| **Block** | `retrieve`, `update`, `delete`, `listChildren`, `appendChildren`, `getAllChildren`, `getAllChildrenRecursive`, `updateTodoChecked` |
| **Database** | `create`, `retrieve`, `update`, `query` |
| **DataSource** | `create`, `retrieve`, `update`, `query` |
| **User** | `me`, `retrieve`, `list` |
| **Search** | `search`, `searchPages`, `searchDatabases` |

---

## Tech Stack

| Library | Role |
|---|---|
| [Ktor Client](https://ktor.io/docs/client.html) | HTTP with per-platform engines (CIO / OkHttp / Darwin / Js) |
| [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON serialization in `commonMain` |
| [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) | Structured concurrency, `StateFlow` for session state |
| [kotlinx.datetime](https://github.com/Kotlin/kotlinx-datetime) | Multiplatform date/time parsing |
| [Koin](https://insert-koin.io/) | Dependency injection wiring |

---

## Testing

The project has two test scopes:

- **`commonTest`** — unit tests using `ktor-client-mock`, covering all repositories without hitting the real API.
- **`jvmTest` live tests** — integration tests (`PageLiveTest`, `DatabaseLiveTest`, `BlockLiveTest`, …) that run against a real Notion workspace when `NOTION_TOKEN` and related IDs are set.

```bash
# Unit tests (all platforms)
./gradlew allTests

# JVM live tests (requires credentials in local.properties)
./gradlew jvmTest
```

---

## Publishing

Releases are published to Maven Central automatically via GitHub Actions when a GitHub release is created:

```
.github/workflows/publish.yml
```

The workflow signs all artifacts with GPG and uploads via the `vanniktech/gradle-maven-publish-plugin`. Credentials are stored as GitHub repository secrets.

---

## Project Structure

```
notion-sdk-kmp/
├── library/
│   └── src/
│       ├── commonMain/    # All SDK logic — repositories, services, models, auth
│       ├── androidMain/   # Ktor Android engine
│       ├── jvmMain/       # Ktor CIO engine
│       ├── jsMain/        # Ktor JS engine
│       ├── appleMain/     # Ktor Darwin engine (shared by iOS + macOS)
│       ├── commonTest/    # Mock-based unit tests
│       └── jvmTest/       # Live integration tests
└── sample/
    ├── NotionClientFromToken.kt     # Basic API token usage
    ├── NotionClientFromProvider.kt  # Custom TokenProvider
    └── NotionClientFromOAuth.kt     # Full OAuth flow with embedded server
```

---

## License

[MIT](LICENSE) © [Tung Doan](https://github.com/tungnk123)
