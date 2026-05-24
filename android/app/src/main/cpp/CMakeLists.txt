cmake_minimum_required(VERSION 3.22.1)
project("openarena2026")

# Path to the engine source (repo root)
set(ENGINE_DIR ${CMAKE_SOURCE_DIR}/../../../../..)

# Android-specific settings
set(ANDROID TRUE)
set(BUILD_STANDALONE 1)
set(BUILD_CLIENT 1)
set(BUILD_SERVER 0)
set(BUILD_GAME_SO 0)
set(BUILD_GAME_QVM 0)
set(USE_OPENGLES 1)
set(USE_INTERNAL_SDL 0)
set(USE_RENDERER_DLOPEN 0)

# Find SDL2
find_package(SDL2 REQUIRED)

# Include engine CMake
add_subdirectory(${ENGINE_DIR} engine-build)

# Create our wrapper library that exports the native activity entry point
add_library(openarena2026 SHARED
    native-main.c
)

target_link_libraries(openarena2026
    SDL2::SDL2
    android
    log
    EGL
    GLESv2
)
