package com.openarena2026;

import org.libsdl.app.SDLActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Game activity that extends SDLActivity.
 * SDL2 handles native library loading and calls SDL_main in the engine.
 */
public class GameActivity extends SDLActivity {

    private static final String TAG = "OpenArena2026";
    private static String[] sArguments = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Build command line arguments for the engine
        String server = getIntent().getStringExtra("server");
        int port = getIntent().getIntExtra("port", 27960);
        String basegame = getIntent().getStringExtra("basegame");

        java.util.List<String> args = new java.util.ArrayList<>();

        // Set the home path to the app's external files directory
        // where baseq3/ or baseoa/ folders are located
        String homePath = getExternalFilesDir(null).getAbsolutePath();
        args.add("+set");
        args.add("fs_homepath");
        args.add(homePath);

        if (basegame != null) {
            args.add("+set");
            args.add("com_basegame");
            args.add(basegame);
        }

        if (server != null) {
            args.add("+connect");
            args.add(server + ":" + port);
        }

        sArguments = args.toArray(new String[0]);

        Log.i(TAG, "Starting engine with args: " + java.util.Arrays.toString(sArguments));

        super.onCreate(savedInstanceState);
    }

    @Override
    protected String[] getArguments() {
        return sArguments;
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {
            "SDL2",
            "main"
        };
    }
}
