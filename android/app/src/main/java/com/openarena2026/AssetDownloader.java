package com.openarena2026;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Downloads OpenArena assets from the internet if not present locally.
 */
public class AssetDownloader extends AsyncTask<Void, Integer, Boolean> {
    private static final String TAG = "AssetDownloader";

    // Official OpenArena 0.8.8 zip from SourceForge (GPL licensed)
    private static final String ASSET_URL =
        "https://sourceforge.net/projects/oarena/files/openarena-0.8.8.zip/download";

    private final Context context;
    private final File destDir;
    private final OnCompleteListener listener;
    private ProgressDialog progressDialog;
    private String errorMsg;

    public interface OnCompleteListener {
        void onComplete(boolean success);
    }

    public AssetDownloader(Context context, File destDir, OnCompleteListener listener) {
        this.context = context;
        this.destDir = destDir;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading OpenArena Assets");
        progressDialog.setMessage("This is a one-time download (~400 MB)...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        File zipFile = new File(context.getCacheDir(), "openarena-0.8.8.zip");

        try {
            // Download zip
            if (!downloadFile(ASSET_URL, zipFile)) {
                return false;
            }

            // Extract pk3 files
            return extractPk3s(zipFile, destDir);

        } catch (Exception e) {
            Log.e(TAG, "Download failed", e);
            errorMsg = e.getMessage();
            return false;
        }
    }

    private boolean downloadFile(String urlString, File dest) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "OpenArena2026/1.0");
            conn.connect();

            int totalSize = conn.getContentLength();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            FileOutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[8192];
            int read;
            long downloaded = 0;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                downloaded += read;
                if (totalSize > 0) {
                    publishProgress((int) ((downloaded * 100) / totalSize));
                }
            }

            out.flush();
            out.close();
            in.close();
            return true;

        } catch (IOException e) {
            errorMsg = "Download error: " + e.getMessage();
            return false;
        }
    }

    private boolean extractPk3s(File zipFile, File destDir) {
        try (ZipInputStream zis = new ZipInputStream(
                new java.io.FileInputStream(zipFile))) {
            ZipEntry entry;
            int count = 0;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".pk3") && name.contains("baseoa/")) {
                    File outFile = new File(destDir, new File(name).getName());
                    FileOutputStream fos = new FileOutputStream(outFile);

                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = zis.read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                    }
                    fos.close();
                    count++;
                    Log.i(TAG, "Extracted: " + outFile.getName());
                }
                zis.closeEntry();
            }

            zipFile.delete(); // Clean up zip
            return count > 0;

        } catch (IOException e) {
            errorMsg = "Extract error: " + e.getMessage();
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        progressDialog.dismiss();
        if (success) {
            Toast.makeText(context, "Assets ready!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed: " + errorMsg, Toast.LENGTH_LONG).show();
        }
        if (listener != null) {
            listener.onComplete(success);
        }
    }
}
