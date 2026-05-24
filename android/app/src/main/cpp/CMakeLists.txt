cmake_minimum_required(VERSION 3.22.1)
project("openarena2026")

# Engine source is at repo root
set(ENGINE_DIR ${CMAKE_SOURCE_DIR}/../../../../../..)

# Android-specific engine settings
set(BUILD_STANDALONE ON CACHE BOOL "" FORCE)
set(BUILD_CLIENT ON CACHE BOOL "" FORCE)
set(BUILD_SERVER OFF CACHE BOOL "" FORCE)
set(BUILD_RENDERER_GL1 OFF CACHE BOOL "" FORCE)
set(BUILD_RENDERER_GL2 ON CACHE BOOL "" FORCE)
set(BUILD_GAME_LIBRARIES OFF CACHE BOOL "" FORCE)
set(BUILD_GAME_QVMS OFF CACHE BOOL "" FORCE)
set(USE_RENDERER_DLOPEN OFF CACHE BOOL "" FORCE)
set(USE_OPENAL OFF CACHE BOOL "" FORCE)
set(USE_VOIP OFF CACHE BOOL "" FORCE)
set(USE_MUMBLE OFF CACHE BOOL "" FORCE)
set(USE_FREETYPE OFF CACHE BOOL "" FORCE)
set(USE_CODEC_VORBIS OFF CACHE BOOL "" FORCE)
set(USE_CODEC_OPUS OFF CACHE BOOL "" FORCE)
set(USE_HTTP OFF CACHE BOOL "" FORCE)
set(USE_INTERNAL_SDL OFF CACHE BOOL "" FORCE)

# Include the engine CMakeLists.txt
add_subdirectory(${ENGINE_DIR} engine-build)

# The engine will build libraries we can link against.
# For now we rely on the engine's own build targets.
