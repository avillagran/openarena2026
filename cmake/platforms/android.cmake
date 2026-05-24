# Android specific settings

if(NOT ANDROID)
    return()
endif()

# Use Unix base sources (sys_unix.c added by unix.cmake)
# con_passive.c is also added by unix.cmake when ANDROID is set

# Android uses Bionic libc, some functions differ
list(APPEND CLIENT_DEFINITIONS ANDROID)

# Disable features not supported on Android
set(USE_OPENAL OFF CACHE BOOL "" FORCE)
set(USE_VOIP OFF CACHE BOOL "" FORCE)
set(USE_MUMBLE OFF CACHE BOOL "" FORCE)
set(USE_CODEC_VORBIS OFF CACHE BOOL "" FORCE)
set(USE_CODEC_OPUS OFF CACHE BOOL "" FORCE)
set(USE_HTTP OFF CACHE BOOL "" FORCE)
set(USE_FREETYPE OFF CACHE BOOL "" FORCE)

# Android doesn't have libdl as separate library
list(APPEND COMMON_LIBRARIES m log android)
