package com.openarena2026;

import android.app.Application;
import android.content.Context;
import java.io.File;

public class OpenArenaApp extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static File getGameDataDir() {
        File dir = new File(appContext.getExternalFilesDir(null), "baseoa");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getQ3DataDir() {
        File dir = new File(appContext.getExternalFilesDir(null), "baseq3");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
