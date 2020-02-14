package com.pandey.popcorn4.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.annotation.Nullable;

public class CameraPreview extends SurfaceView
        implements SurfaceHolder.Callback2, Camera.PictureCallback {


    @Nullable
    private Camera mCamera;

    @NonNull
    private SurfaceHolder mSurfaceHolder;

    @NonNull
    private Context mContext;

    @Nullable
    private CamerapPreviewListener mCameraPreviewListener;

    public CameraPreview(@NonNull Context context) {
        super(context);
        mContext =context;

        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCameraPreviewListener(@NonNull CamerapPreviewListener cameraPreviewListener) {
        this.mCameraPreviewListener = cameraPreviewListener;
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = getCamera();
        Camera.Parameters cameraParameters = mCamera.getParameters();
        // set image quality
        cameraParameters.setJpegQuality(100); // 100 for the best quality
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mSurfaceHolder.getSurface() == null){
            return;
        }
        // stop preview before making changes
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
            }
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
        }
    }

    private static Camera getCamera() {
        Camera camera =  null;
        try {
            camera = Camera.open();
        } catch (Exception e){
            e.printStackTrace();
        }
        return camera;
    }

    public void takePicture() {
        if (mCamera != null) {
            if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)) {
                // if app has camera
                mCamera.autoFocus((success, camera) -> {
                    if (success) {
                        mCamera.takePicture(null, null, this);
                    } else {
                        if (mCameraPreviewListener != null) {
                            mCameraPreviewListener.onFailure();
                        }
                    }
                });
            } else {
                mCamera.takePicture(null, null, this);
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null && data.length > 0
                && mCameraPreviewListener != null) {
            mCameraPreviewListener.onSuccess(data);
        } else {
            mCameraPreviewListener.onFailure();
        }
    }

    public interface CamerapPreviewListener {

        void onSuccess(@NonNull byte[] data);

        void onFailure();
    }
}
