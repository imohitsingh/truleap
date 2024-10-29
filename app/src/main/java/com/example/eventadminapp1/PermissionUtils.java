package com.example.eventadminapp1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int STORAGE_PERMISSION_CODE = 101;

    // Method to request storage permission
    public static void requestStoragePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            // Permission already granted
            Toast.makeText(activity, "Storage permission is already granted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the permission result
    public static boolean handlePermissionResult(Context context, int requestCode, int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return true;  // Permission granted
            } else {
                Toast.makeText(context, "Storage permission is required to select an image", Toast.LENGTH_SHORT).show();
                return false;  // Permission denied
            }
        }
        return false;
    }
}

