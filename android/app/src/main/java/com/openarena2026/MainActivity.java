package com.openarena2026;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OpenArena2026";

    // Featured servers - ggup.cl community
    private static final String SERVER_Q3 = "q3.ggup.cl";
    private static final int PORT_Q3 = 27960;
    private static final String SERVER_CTF = "q3ctf.ggup.cl";
    private static final int PORT_CTF = 27961;

    private ActivityResultLauncher<Intent> importLauncher;
    private ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkOpenArenaAssets();

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnImportQ3 = findViewById(R.id.btnImportQ3);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnServers = findViewById(R.id.btnServers);

        btnPlay.setOnClickListener(v -> launchGame());
        btnImportQ3.setOnClickListener(v -> startQ3Import());
        btnSettings.setOnClickListener(v -> showSettings());
        btnServers.setOnClickListener(v -> showServers());

        importLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        importPakFile(uri);
                    }
                }
            }
        );

        permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {}
        );

        requestPermissionsIfNeeded();
    }

    private void requestPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(new String[]{
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES
            });
        } else {
            permissionLauncher.launch(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
            });
        }
    }

    private void checkOpenArenaAssets() {
        File baseoa = OpenArenaApp.getGameDataDir();
        File[] pk3s = baseoa.listFiles((dir, name) -> name.endsWith(".pk3"));
        if (pk3s == null || pk3s.length == 0) {
            showAssetMissingDialog();
        }
    }

    private void showAssetMissingDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Assets Not Found")
            .setMessage("OpenArena assets not found. You can:\n\n" +
                       "1. Download them now (~400 MB)\n" +
                       "2. Place .pk3 files manually in:\n" + OpenArenaApp.getGameDataDir().getAbsolutePath() + "\n\n" +
                       "3. Import Quake III Arena pak files via Import Q3A")
            .setPositiveButton("Download Now", (dialog, which) -> downloadAssets())
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void downloadAssets() {
        new AssetDownloader(this, OpenArenaApp.getGameDataDir(), success -> {
            if (success) {
                Toast.makeText(this, "Ready to play!", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void launchGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void launchGameConnect(String server, int port) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("server", server);
        intent.putExtra("port", port);
        startActivity(intent);
    }

    private void startQ3Import() {
        new AlertDialog.Builder(this)
            .setTitle("Import Quake III Arena")
            .setMessage("Select pak0.pk3 through pak8.pk3 from your original Quake III Arena copy.")
            .setPositiveButton("Select File", (dialog, which) -> openFilePicker())
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        importLauncher.launch(intent);
    }

    private void importPakFile(Uri uri) {
        try {
            String fileName = getFileName(uri);
            if (!fileName.endsWith(".pk3")) {
                Toast.makeText(this, "Only .pk3 files allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            File destDir = OpenArenaApp.getQ3DataDir();
            File destFile = new File(destDir, fileName);

            try (InputStream in = getContentResolver().openInputStream(uri);
                 OutputStream out = new FileOutputStream(destFile)) {
                if (in != null) {
                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
            }

            Toast.makeText(this, "Imported: " + fileName, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Imported Q3 asset to: " + destFile.getAbsolutePath());

        } catch (IOException e) {
            Log.e(TAG, "Error importing file", e);
            Toast.makeText(this, "Import error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getFileName(Uri uri) {
        String result = uri.getLastPathSegment();
        if (result == null) result = "unknown.pk3";
        return result;
    }

    private void showSettings() {
        Toast.makeText(this, "Settings - Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private void showServers() {
        final CharSequence[] servers = {
            SERVER_Q3 + ":" + PORT_Q3 + " (FFA/TDM)",
            SERVER_CTF + ":" + PORT_CTF + " (CTF)",
            "Other server..."
        };

        new AlertDialog.Builder(this)
            .setTitle("Featured Servers - ggup.cl")
            .setItems(servers, (dialog, which) -> {
                switch (which) {
                    case 0:
                        launchGameConnect(SERVER_Q3, PORT_Q3);
                        break;
                    case 1:
                        launchGameConnect(SERVER_CTF, PORT_CTF);
                        break;
                    case 2:
                        Toast.makeText(this, "Custom server browser - Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                }
            })
            .show();
    }
}
