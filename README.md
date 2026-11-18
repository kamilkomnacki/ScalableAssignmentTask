# Scalable Capital — Android Developer Assignment

A native Android application developed as a submission for the **Scalable Capital Android Developer Assignment**. Written in **Kotlin** with **Jetpack Compose** and **Material 3**, it allows users to browse public GitHub repositories and explore their commit history.

## Scope and time budget

Development followed an approximately **four-hour time budget**, in line with the assignment's time constraint. The implementation prioritizes the core browsing flow, readable code, and clear separation between networking, state management, and UI. Some requested functionality remains incomplete within this scope.

## Implemented functionality

- Public repository listing for the configured GitHub account, currently `mattpocock`.
- Commit history with commit titles, authors, dates, and abbreviated SHAs.
- Pagination for both repository and commit lists.
- Loading, empty, and error states with retry actions.
- Compose previews and JVM tests for data handling.

Networking uses Retrofit and Gson. ViewModels expose UI state through Kotlin Coroutines and StateFlow, with dependencies wired manually.

## Remaining work

The monthly commit activity visualization, automatic cycling between months, and optional bar animation have not been implemented.

## Running locally

1. Open the project in Android Studio and install Android SDK 37.
2. Configure JDK 25 for Gradle, as specified in `gradle/gradle-daemon-jvm.properties`, and sync the project.
3. Run the `app` configuration on a device or emulator running Android 9 (API 28) or later.

An internet connection is required. The app currently uses unauthenticated requests to the public GitHub REST API. To change the account, update `ScalableApplication.ACCOUNT`.

## Build and checks

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
./gradlew :app:lintDebug
```
