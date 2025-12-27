# GitHub Releases Setup for DrinkOrder APK

This document explains how to use GitHub Releases to automatically build and distribute APK files for the DrinkOrder Android application.

## Overview

The project is now configured with GitHub Actions that automatically:
1. **Build APK files** (both debug and release versions) when you create a new release tag
2. **Create a GitHub Release** with proper release notes
3. **Attach APK files** to the release for easy download

## How It Works

### Automated Workflow
- **Trigger**: Pushing a version tag (like `v1.0.0`, `v2.1.3`, etc.)
- **Builds**: Both debug and release APK variants
- **Uploads**: APK files are automatically attached to the GitHub release

### File Structure
```
.github/
└── workflows/
    └── release.yml          # GitHub Actions workflow
app/
├── build.gradle.kts         # Updated with signing config
└── build/outputs/apk/       # Built APK files (generated during build)
```

## Creating a Release

### Step 1: Prepare Your Code
1. Make sure all your changes are committed and pushed to the main branch
2. Update the `versionName` and `versionCode` in `app/build.gradle.kts` if needed:
   ```kotlin
   defaultConfig {
       versionCode = 2        // Increment for each release
       versionName = "1.1.0"  // Update version string
       // ...
   }
   ```

### Step 2: Create and Push a Version Tag
```bash
# Create a new tag (replace with your version)
git tag v1.0.0

# Push the tag to GitHub
git push origin v1.0.0
```

### Step 3: Monitor the Build
1. Go to your GitHub repository
2. Click on the "Actions" tab
3. You'll see the "Build and Release APK" workflow running
4. Wait for it to complete (usually 5-10 minutes)

### Step 4: Check the Release
1. Go to your repository's "Releases" section
2. You'll find a new release with:
   - **Debug APK**: `drinkorder-v1.0.0-debug.apk` (for testing)
   - **Release APK**: `drinkorder-v1.0.0-release-unsigned.apk` (for distribution)

## APK Types Explained

### Debug APK
- **Purpose**: Development and testing
- **Features**: Includes debug information, easier to debug
- **Security**: Less secure, not meant for production
- **File name**: `drinkorder-v{version}-debug.apk`

### Release APK (Unsigned)
- **Purpose**: Production distribution
- **Features**: Optimized build, smaller size
- **Security**: More secure but currently unsigned
- **File name**: `drinkorder-v{version}-release-unsigned.apk`

## App Signing (Optional Advanced Setup)

Currently, the release APK is **unsigned**. For production apps, you should set up proper signing:

### Setting Up Signing (Optional)
1. **Generate a keystore** (one-time setup):
   ```bash
   keytool -genkey -v -keystore release-key.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Add GitHub Secrets** in your repository settings:
   - `SIGNING_STORE_FILE`: Base64 encoded keystore file
   - `SIGNING_STORE_PASSWORD`: Keystore password
   - `SIGNING_KEY_ALIAS`: Key alias (usually "release")
   - `SIGNING_KEY_PASSWORD`: Key password

3. **Update the workflow**: The workflow is already configured to use these secrets when available.

## Installation Instructions for Users

### For Android Users:
1. **Download** the APK file from the GitHub release
2. **Enable** "Install from Unknown Sources" in Android Settings:
   - Go to Settings > Security (or Privacy)
   - Enable "Unknown Sources" or "Install from Unknown Sources"
3. **Install** the APK by tapping on the downloaded file
4. **Launch** the DrinkOrder app from your app drawer

### Security Note
Installing APK files from outside the Google Play Store requires enabling "Unknown Sources." This is normal for sideloading apps but users should only install APKs from trusted sources.

## Troubleshooting

### Build Failures
- **Check the Actions tab** in GitHub for error messages
- **Common issues**: 
  - Gradle build errors (check `build.gradle.kts` syntax)
  - Missing dependencies
  - Android SDK version conflicts

### Missing APK Files
- Ensure the workflow completed successfully
- Check that the APK paths in the workflow match your project structure
- Verify that the Gradle build commands work locally

### Version Conflicts
- Make sure to increment `versionCode` for each release
- Use semantic versioning for tags (v1.0.0, v1.0.1, etc.)

## Technical Details

### Build Environment
- **OS**: Ubuntu Latest (GitHub hosted runner)
- **Java**: JDK 17 (Temurin distribution)
- **Android SDK**: Latest available
- **Gradle**: Uses project's Gradle wrapper

### Workflow Features
- **Caching**: Gradle dependencies are cached for faster builds
- **Parallel builds**: Debug and release variants built simultaneously
- **Automatic versioning**: Version extracted from Git tag
- **Rich release notes**: Formatted release description with download instructions

## Customization

You can customize the release process by modifying `.github/workflows/release.yml`:
- **Change trigger conditions** (different tag patterns)
- **Add more build variants** (staging, beta, etc.)
- **Customize release notes** template
- **Add additional checks** (tests, linting, etc.)
- **Upload to different platforms** (Firebase App Distribution, etc.)

## Current Configuration Summary

✅ **GitHub Actions workflow** configured
✅ **Automatic APK building** on tag push  
✅ **Debug and Release variants** supported
✅ **Java 17 compatibility** updated
✅ **Signing configuration** ready (optional)
✅ **Release notes** auto-generated
✅ **APK file attachment** to releases

The system is ready to use! Simply create a version tag and push it to trigger your first automated release.