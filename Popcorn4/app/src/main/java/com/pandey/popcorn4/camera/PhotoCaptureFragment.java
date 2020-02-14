package com.pandey.popcorn4.camera;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;

import butterknife.BindView;

public class PhotoCaptureFragment
        extends BaseFragment<PhotoCaptureFragment.PhotoCaptureFragmentListener> implements CameraPreview.CamerapPreviewListener {

    @BindView(R.id.camera_preview_container)
    FrameLayout vCameraPreviewContainer;

    @BindView(R.id.take_picture_button)
    Button vTakePictureButton;

    @Nullable
    private CameraPreview mCameraPreview;

    public static PhotoCaptureFragment newInstance() {
        PhotoCaptureFragment captureFragment = new PhotoCaptureFragment();
        Bundle bundle = new Bundle();
        captureFragment.setArguments(bundle);
        return captureFragment;
    }

    @Override
    public void initLayout() {
        mCameraPreview = new CameraPreview(getContext());
        mCameraPreview.setCameraPreviewListener(this);
        vCameraPreviewContainer.addView(mCameraPreview);
    }

    @Override
    public void initListeners() {
        vTakePictureButton.setOnClickListener(
                v -> {
                    if (mCameraPreview != null) {
                        mCameraPreview.takePicture();
                    }
                }
        );
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return null;
    }

    @NonNull
    @Override
    protected Class<PhotoCaptureFragmentListener> getListenerClass() {
        return PhotoCaptureFragmentListener.class;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_photo_capture;
    }


    @Override
    public void onSuccess(@NonNull byte[] data) {
        //use image data
    }

    @Override
    public void onFailure() {

    }

    public interface PhotoCaptureFragmentListener {

    }
}
