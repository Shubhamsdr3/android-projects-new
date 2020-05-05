package com.pandey.popcorn4.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_capture.*
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class PhotoCaptureFragment : BaseFragment<PhotoCaptureFragment.PhotoCaptureFragmentListener>(), CameraPreview.CameraPreviewListener {

    private var mCameraPreview: CameraPreview? = null

    companion object {
        fun newInstance() : PhotoCaptureFragment {
            return PhotoCaptureFragment()
        }
    }

    override fun initLayout() {
        mCameraPreview = CameraPreview(context!!)
        mCameraPreview?.setCameraPreviewListener(this)
        camera_preview_container.addView(mCameraPreview)
        photo_capture_loader.visibility = View.GONE
    }

    override fun initListeners() {
        take_picture_button.setOnClickListener {
            if (mCameraPreview != null) {
                mCameraPreview!!.takePicture()
            }
        }
    }

    override fun onSuccess(data: ByteArray) {
        var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        val mtx = Matrix()
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, mtx, true)
        saveImage(bitmap)
    }

    private fun saveImage(bitmap: Bitmap?) {
        val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
            Timber.d("Error creating media file, check storage permissions")
            return
        }
        try {
            val fos = FileOutputStream(pictureFile)
            val outputStream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val data = outputStream.toByteArray()
            Timber.d("Writing to file")
            fos.write(data)
            fos.close()
        } catch (e: FileNotFoundException) {
            Timber.d("File not found: ${e.message}")
        } catch (e: IOException) {
            Timber.d( "Error accessing file: ${e.message}")
        }
    }

    // Don't forget to request permission
    private fun getOutputMediaFile(type: Int): File? {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "popcorn")

        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    Log.d("Popcorn", "failed to create directory")
                    return null
                }
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }

    override fun getToolBar(): FrameLayout? {
        return null
    }

    override fun getListenerClass(): Class<PhotoCaptureFragmentListener> {
        return PhotoCaptureFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_photo_capture
    }

    override fun onFailure() {
        TODO("Not yet implemented")
    }

    interface PhotoCaptureFragmentListener {

    }

}