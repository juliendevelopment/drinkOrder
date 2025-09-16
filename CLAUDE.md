# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

DrinkOrder is an Android application built with Kotlin and Jetpack Compose. The app uses modern Android development practices with the latest SDK versions.

## Development Setup

This project requires:
- Android SDK 34 (compile target)
- Minimum SDK 24
- Kotlin 1.9.10
- Gradle 8.5.2
- Java 8 compatibility

## Common Commands

```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Build release APK  
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean

# Install debug APK to device
./gradlew installDebug
```

## Architecture Overview

- **Package Structure**: `com.drinkorder`
- **UI Framework**: Jetpack Compose with Material 3
- **Build System**: Gradle with Kotlin DSL
- **Theme**: Material 3 with dynamic color support (Android 12+)
- **Main Activity**: `MainActivity.kt` using ComponentActivity with Compose

## Key Dependencies

- AndroidX Core KTX 1.12.0
- Jetpack Compose BOM 2023.10.01
- Material 3 Design System
- Activity Compose for integration
- Standard testing libraries (JUnit, Espresso)

## Development Notes

- Uses ViewBinding and Compose build features
- Configured for both light/dark themes
- Supports RTL languages
- Includes backup and data extraction rules for privacy