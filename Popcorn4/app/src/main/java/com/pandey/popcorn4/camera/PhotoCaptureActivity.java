package com.pandey.popcorn4.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pandey.popcorn4.base.BaseActivity;

public class PhotoCaptureActivity extends BaseActivity implements PhotoCaptureFragment.PhotoCaptureFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.CAMERA
                    },
                    50
            );
        }
        startFragment(PhotoCaptureFragment.Companion.newInstance(), true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
