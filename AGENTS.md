# Repository Guidelines

## Project Structure & Module Organization

The single Android module is `app`. Production Kotlin lives in `app/src/main/java/com/kamilkomnacki/scalableassignmenttask/`: `data/` handles GitHub networking and models, `pagination/` manages page loading, and `ui/` contains screens, state, components, and themes. Resources are in `app/src/main/res/`; JVM tests in `app/src/test/`; device tests in `app/src/androidTest/`.

Preserve the two-layer design, manual dependency wiring in `ScalableApplication`, and Activity-based navigation. Keep data independent of UI; place Compose pagination adapters in `ui/pagination/`.

## Build, Test, and Development Commands

Use Android SDK 37 and the JDK 25 Gradle daemon configuration.

## Scope & Pagination Gotchas

Read [README.md](README.md) before expanding assignment scope. 
