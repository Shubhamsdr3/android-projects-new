package com.pandey.popcorn4.camera

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_camera_new.*
import timber.log.Timber
import java.io.*
import java.util.*

class CameraNewFragment : BaseFragment<CameraNewFragment.CameraNewFragmentListener>() {

    private val ORIENTATION = SparseIntArray()

    init {
        ORIENTATION.append(Surface.ROTATION_0, 90)
        ORIENTATION.append(Surface.ROTATION_90, 0)
        ORIENTATION.append(Surface.ROTATION_180, 270)
        ORIENTATION.append(Surface.ROTATION_270, 180)
    }

    private lateinit var cameraId: String

    private var cameraDevice: CameraDevice? = null

    private lateinit var cameraCaptureSession: CameraCaptureSession

    private lateinit var captureRequestBuilder: CaptureRequest.Builder

    private lateinit var imageDimension: Size

    // saving to file
    private lateinit var file : File

    private var backgroundHandler: Handler? = null

    private var handlerThread: HandlerThread? = null

    private var cameraDeviceCallback : CameraDevice.StateCallback? = null

    private var textureViewListener: TextureView.SurfaceTextureListener? = null

    companion object {
        fun newInstance() : CameraNewFragment {
            return CameraNewFragment()
        }
    }

    override fun initLayout() {
       cameraDeviceCallback = object : CameraDevice.StateCallback() {

            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                createCameraPreview()
            }

            override fun onDisconnected(camera: CameraDevice) {
                cameraDevice?.close()
            }

            override fun onError(camera: CameraDevice, error: Int) {
                cameraDevice?.close()
                cameraDevice = null
            }
        }

        textureViewListener =  object : TextureView.SurfaceTextureListener {

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                openCamera()
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                return true
            }

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                openCamera()
            }
        }
    }

    override fun initListeners() {
        texture_view.surfaceTextureListener = textureViewListener

        capture_image_btn.setOnClickListener {
            takePicture()
        }

    }

    private fun openCamera() {
        val cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager.cameraIdList[0]
            val cameraCharacterStatics = cameraManager.getCameraCharacteristics(cameraId)
            val streamConfigurationMap = cameraCharacterStatics[CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP]
            if (streamConfigurationMap != null) {
                imageDimension = streamConfigurationMap.getOutputSizes(SurfaceTexture::class.java)[0]
            }

            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            cameraManager.openCamera(cameraId, cameraDeviceCallback!!,null)
        } catch (e: CameraAccessException) {
            Timber.e(e)
        }
    }

    private fun takePicture() {
        if (cameraDevice ==  null) {
            return
        }
        val cameraManager  = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraDevice?.id!!)
            val jpegSizes : Array<Size>? = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)?.getOutputSizes(ImageFormat.JPEG)

            // capture image with custom size
            var width = 640
            var height = 480
            if (jpegSizes != null && jpegSizes.isNotEmpty()) {
                width = jpegSizes[0].width
                height = jpegSizes[0].height
            }
            val imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurface = mutableListOf<Surface>()
            outputSurface.add(imageReader.surface)
            outputSurface.add(Surface(texture_view.surfaceTexture))

            val captureRequestBuilder : CaptureRequest.Builder? = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureRequestBuilder?.addTarget(imageReader.surface)
            captureRequestBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            // check orientation based on device
            val rotation = activity?.windowManager?.defaultDisplay?.rotation
            captureRequestBuilder?.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATION.get(rotation!!))

            file = File(Environment.getExternalStorageDirectory().toString() + "/"  + UUID.randomUUID() + ".jpg")
            val imageReaderListener = ImageReader.OnImageAvailableListener {
                var image: Image? = null
                try {
                    image = imageReader.acquireLatestImage()
                    val byteBuilder = image.planes[0].buffer
                    val bytes = ByteArray(byteBuilder.capacity())
                    byteBuilder.get(bytes)
                    saveImage(bytes)
                } catch (e: FileNotFoundException) {
                    Timber.e(e)
                } catch (io: IOException) {
                    Timber.e(io)
                } finally {
                    image?.close()
                }
            }

            imageReader.setOnImageAvailableListener(imageReaderListener, backgroundHandler)

            val captureListener = object : CameraCaptureSession.CaptureCallback() {

                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                    super.onCaptureCompleted(session, request, result)
                    Toast.makeText(activity, "Image saved file : $file", Toast.LENGTH_SHORT).show()
                    createCameraPreview()
                }
            }

            cameraDevice?.createCaptureSession(outputSurface, object : CameraCaptureSession.StateCallback() {

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    try {
                        cameraCaptureSession.capture(captureRequestBuilder?.build()!!, captureListener, backgroundHandler)
                    } catch (e: CameraAccessException) {
                        Timber.e(e)
                    }
                }

                override fun onConfigured(session: CameraCaptureSession) {

                }
            }, backgroundHandler)

        } catch (e: CameraAccessException) {
            Timber.e(e)
        }
    }

    private fun createCameraPreview() {
        try {
            val surfaceTexture = texture_view.surfaceTexture
            surfaceTexture?.setDefaultBufferSize(imageDimension.width, imageDimension.height)
            val surface = Surface(surfaceTexture)
            captureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)!!
            captureRequestBuilder.addTarget(surface)
            cameraDevice?.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    if (cameraDevice == null) {
                        return
                    }
                    cameraCaptureSession = session
                    updatePreview()
                }
                override fun onConfigured(session: CameraCaptureSession) {
                    Toast.makeText(context, "Changed..", Toast.LENGTH_SHORT).show()
                }
            }, null)
        } catch (e: CameraAccessException) {
            Timber.e(e)
        }
    }

    private fun updatePreview() {
        if (cameraDevice == null) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)

        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler)
        } catch (e: CameraAccessException) {
            Timber.e(e)
        }
    }

    private fun saveImage(bytes: ByteArray)  {
        try {
            val outputStream = FileOutputStream(file)
            outputStream.write(bytes)
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    override fun getToolBar(): FrameLayout? {
        return null
    }

    override fun getListenerClass(): Class<CameraNewFragmentListener> {
        return CameraNewFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_camera_new
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (texture_view.isAvailable) {
            openCamera()
        } else {
            texture_view.surfaceTextureListener = textureViewListener
        }
    }

    override fun onPause() {
        super.onPause()
        stopBackgroundThread()
    }

    private fun stopBackgroundThread() {
        handlerThread?.quitSafely()
        try {
            handlerThread?.join()
            handlerThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            Timber.e(e)
        }
    }

    private fun startBackgroundThread() {
        handlerThread = HandlerThread("camera background")
        handlerThread?.start()
        backgroundHandler = Handler(handlerThread?.looper)
    }

    interface CameraNewFragmentListener {

    }
}