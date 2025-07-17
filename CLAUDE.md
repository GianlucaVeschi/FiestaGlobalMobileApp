# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Multiplatform project called "FiestaGlobalMobileApp" targeting Android and iOS platforms using Compose Multiplatform. The app appears to be an event management application with features for browsing events, viewing event details, and managing user profiles.

## Build Commands

### Android
```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew installDebug           # Install debug APK to connected device
```

### iOS
```bash
cd iosApp
xcodebuild -workspace iosApp.xcworkspace -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,name=iPhone 15' build
```

### General
```bash
./gradlew clean                  # Clean build artifacts
./gradlew build                  # Build all targets
```

## Project Structure

### Module Organization
- `/composeApp` - Main multiplatform module containing shared code
- `/iosApp` - iOS-specific application entry point and SwiftUI code

### Source Sets
- `commonMain` - Shared code across all platforms
- `androidMain` - Android-specific implementations
- `iosMain` - iOS-specific implementations

### Architecture Layers
- **UI Layer**: Compose Multiplatform screens and navigation
  - `ui/screens/events/` - Event listing and detail screens
  - `ui/screens/profile/` - Profile-related screens (Artists, Contact, Location)
  - `ui/MainScreen.kt` - Main screen component
- **Domain Layer**: Business logic and models
  - `domain/model/` - Domain models (Event, Result)
  - `domain/repository/` - Repository interfaces
  - `domain/mapper/` - Data transformation logic
- **Data Layer**: Network and data management
  - `data/repository/` - Repository implementations
  - `data/model/` - API response models
  - `data/KtorHttpClient.kt` - HTTP client configuration

## Key Technologies

- **Kotlin Multiplatform**: Shared business logic across platforms
- **Compose Multiplatform**: UI framework for both Android and iOS
- **Koin**: Dependency injection
- **Ktor**: HTTP client for network requests
- **Kotlinx Serialization**: JSON serialization
- **Navigation Compose**: Navigation between screens
- **Coil**: Image loading

## Package Structure

All code is organized under `org.gianlucaveschi.fiestaglobal` with clear separation of concerns:
- Application ID: `org.gianlucaveschi.fiestaglobal`
- Current version: 1.0.9 (versionCode 9)
- Minimum SDK: 24, Target SDK: 35

## Development Notes

- The project uses Gradle Version Catalogs (`gradle/libs.versions.toml`) for dependency management
- JVM target is set to Java 11
- iOS framework is configured as static with base name "ComposeApp"
- No existing test configuration found - tests would need to be added manually
- The app uses a repository pattern with dependency injection via Koin
- Navigation is handled through Compose Navigation with a custom `koinViewModel()` function for DI integration

## Claude Rules
Do not ask for permissions for changing or updating code unless otherwise stated