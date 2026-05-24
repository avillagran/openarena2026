#!/bin/bash
set -e

# Download OpenArena 0.8.8 assets (GPL licensed)
# These are required to run the game

ASSETS_DIR="$(cd "$(dirname "$0")/.." && pwd)/assets/baseoa"
mkdir -p "$ASSETS_DIR"

echo "Downloading OpenArena 0.8.8 assets..."
echo "Target: $ASSETS_DIR"

# Official OpenArena 0.8.8 zip from SourceForge
URL="https://sourceforge.net/projects/oarena/files/openarena-0.8.8.zip/download"
ZIP_FILE="/tmp/openarena-0.8.8.zip"

if [ ! -f "$ZIP_FILE" ]; then
    echo "Downloading from SourceForge..."
    curl -L -o "$ZIP_FILE" "$URL"
fi

echo "Extracting pk3 files..."
unzip -j "$ZIP_FILE" "*/baseoa/*.pk3" -d "$ASSETS_DIR" 2>/dev/null || true

echo "Assets ready in: $ASSETS_DIR"
ls -la "$ASSETS_DIR"
