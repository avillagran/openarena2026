package com.openarena2026;

import android.app.NativeActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Native activity that runs the OpenArena engine.
 * The engine is loaded as a native library (.so) and handles its own rendering.
 */
public class GameActivity extends NativeActivity {

    // static {
    //     System.loadLibrary("openarena2026");
    // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Native engine not yet built. Showing placeholder.
        Toast.makeText(this, "Native engine coming soon!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }
}
