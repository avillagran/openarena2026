#!/bin/bash
set -e

# Build script for OpenArena2026 Android

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
ANDROID_DIR="$PROJECT_DIR/android"

echo "Building OpenArena2026 for Android..."
echo "Project: $PROJECT_DIR"

cd "$ANDROID_DIR"

# Check for Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "ERROR: ANDROID_HOME not set"
    exit 1
fi

# Use latest NDK
NDK_VERSION=$(ls -1 "$ANDROID_HOME/ndk" | sort -V | tail -1)
export ANDROID_NDK_HOME="$ANDROID_HOME/ndk/$NDK_VERSION"
echo "Using NDK: $NDK_VERSION"

# Build
./gradlew assembleDebug

echo "Build complete. APK located at:"
find "$ANDROID_DIR/app/build/outputs/apk" -name "*.apk" -type f
