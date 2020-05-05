package com.pandey.popcorn4.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.annotation.Nullable;

import timber.log.Timber;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback2, Camera.PictureCallback {

    @Nullable
    private Camera mCamera;

    @Nullable
    private SurfaceHolder mSurfaceHolder;

    @Nullable
    private Context mContext;

    @Nullable
    private CameraPreviewListener mCameraPreviewListener;

    public CameraPreview(@NonNull Context context) {
        super(context);
        mContext = context;
        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCameraPreviewListener(@NonNull CameraPreviewListener cameraPreviewListener) {
        this.mCameraPreviewListener = cameraPreviewListener;
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = getCamera();
        Camera.Parameters cameraParameters = mCamera.getParameters();
//        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        // set image quality
        cameraParameters.setJpegQuality(100); // 100 for the best quality

        // correct the orientation
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            cameraParameters.set("orientation", "portrait");
            mCamera.setDisplayOrientation(90);
            cameraParameters.setRotation(90);
        } else  {
            cameraParameters.set("orientation", "landscape");
            mCamera.setDisplayOrientation(0);
            cameraParameters.setRotation(0);
        }
        mCamera.setParameters(cameraParameters);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder != null && mSurfaceHolder.getSurface() == null){
            return;
        }
        // stop preview before making changes
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
            }
        } catch (Exception e){
            Timber.e(e);
        }

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

    //singleton
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
            if (mContext != null && mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)) {
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

    /**
     * Called when image data is available after a picture is taken.
     * The format of the data depends on the context of the callback
     * and {@link Camera.Parameters} settings.
     *
     * @param data a byte array of the picture data
     * @param camera the Camera service object
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null && data.length > 0 && mCameraPreviewListener != null) {
            mCameraPreviewListener.onSuccess(data);
        } else {
            if (mCameraPreviewListener != null) {
                mCameraPreviewListener.onFailure();
            }
        }
    }

    public interface CameraPreviewListener {

        void onSuccess(@NonNull byte[] data);

        void onFailure();
    }
}
