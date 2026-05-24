package com.openarena2026;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Game activity. Currently a placeholder until the native engine is built.
 * Will become NativeActivity once libopenarena2026.so is compiled.
 */
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Immersive fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        String server = getIntent().getStringExtra("server");
        int port = getIntent().getIntExtra("port", 27960);
        String basegame = getIntent().getStringExtra("basegame");

        StringBuilder msg = new StringBuilder("Native engine coming soon!\n");
        if (server != null) {
            msg.append("Server: ").append(server).append(":").append(port).append("\n");
        }
        if (basegame != null) {
            msg.append("Game: ").append(basegame).append("\n");
        }
        msg.append("\nPress back to return.");

        Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show();
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
