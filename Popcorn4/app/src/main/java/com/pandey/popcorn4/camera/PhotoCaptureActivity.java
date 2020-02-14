package com.pandey.popcorn4.camera;

import android.os.Bundle;

import com.pandey.popcorn4.base.BaseActivity;

public class PhotoCaptureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startFragment(PhotoCaptureFragment.newInstance(), true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
