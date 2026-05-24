#include <android/native_activity.h>
#include <dlfcn.h>
#include <errno.h>
#include <android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "OpenArena2026", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "OpenArena2026", __VA_ARGS__))

// Forward declaration to the engine's main
extern int main(int argc, char *argv[]);

void ANativeActivity_onCreate(ANativeActivity* activity, void* savedState, size_t savedStateSize) {
    LOGI("NativeActivity onCreate");

    int argc = 1;
    char* argv[] = { "openarena2026", NULL };
    main(argc, argv);
}
