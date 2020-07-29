package com.pandey.popcorn4.chat.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.FrameLayout
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import com.pandey.popcorn4.base.InteractionListener
import com.pandey.popcorn4.chat.SigningActivity
import com.pandey.popcorn4.chat.glide.GlideApp
import com.pandey.popcorn4.chat.util.FireStoreUtil
import com.pandey.popcorn4.chat.util.StorageUtil
import kotlinx.android.synthetic.main.fragment_account.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import timber.log.Timber
import java.io.ByteArrayOutputStream

class AccountFragment : BaseFragment<AccountFragment.AccountFragmentListener>() {

    private val IMAGE_REQUEST_CODE = 201

    private lateinit var selectedImage: ByteArray

    private var isPictureChanged = false

    companion object {
        fun newInstance() : AccountFragment {
            return AccountFragment()
        }
    }

    override fun initLayout() {

    }

    override fun initListeners() {
        profile_picture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_REQUEST_CODE)
        }

        save_button.setOnClickListener {
            Timber.d("Saving to firestore....")
            if (::selectedImage.isInitialized) {
                StorageUtil.uploadProfilePicture(selectedImage) {
                    FireStoreUtil.updateCurrentUser(et_name.text.toString(), et_bio.text.toString(), it)
                }
                Toast.makeText(context, "Saved user profile..", Toast.LENGTH_SHORT).show()
            } else {
                FireStoreUtil.updateCurrentUser(et_name.text.toString(), et_bio.text.toString(), null)
                Toast.makeText(context, "Updated user profile data..", Toast.LENGTH_SHORT).show()
            }

        }

        signout_button.setOnClickListener {
            AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                       startActivity(activity?.intentFor<SigningActivity>()?.newTask()?.clearTask())
                    }
        }
    }

    override fun onStart() {
        super.onStart()

        FireStoreUtil.getCurrentUser {
            if (isVisible) {
                et_name.setText(it?.name)
                et_bio.setText(it?.bio)
                if (!isPictureChanged && it?.profilePicturePath != null) {
                    GlideApp.with(this).load(StorageUtil.pathToReference(it.profilePicturePath))
                            .placeholder(R.drawable.ic_account_circle_black_24dp)
                            .into(profile_picture)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE && data != null) {
            val imagePath = data.data
            val imageBitMap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imagePath)
            val outputStream = ByteArrayOutputStream()
            imageBitMap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImage = outputStream.toByteArray()
            GlideApp.with(this).load(selectedImage).into(profile_picture)
            isPictureChanged = true
        }
    }

    override fun getToolBar(): FrameLayout? {
       return null
    }

    override fun getListenerClass(): Class<AccountFragmentListener> {
       return AccountFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_account
    }

    interface AccountFragmentListener : InteractionListener {

    }
}