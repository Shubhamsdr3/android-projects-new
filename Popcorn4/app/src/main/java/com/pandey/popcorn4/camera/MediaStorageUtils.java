package com.pandey.popcorn4.camera;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MediaStorageUtils {

    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;

    private static final String APP_NAME = "POPCORN_";

    /**
     * Create a File for saving an image or video
     * persists the media file even after the app is un-installed
     */
    private static File getOutputMediaFile(int type, String fileName) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName
        );

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            return null;
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(
                    mediaStorageDir.getPath() +
                            File.separator +
                            "_" +
                            APP_NAME +
                            fileName +
                            timeStamp +
                            ".jpg"
            );
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(
                    mediaStorageDir.getPath() +
                            File.separator +
                            "_" +
                            APP_NAME +
                            fileName +
                            timeStamp +
                            ".mp4"
            );
        } else {
            return null;
        }
        return mediaFile;
    }


}
