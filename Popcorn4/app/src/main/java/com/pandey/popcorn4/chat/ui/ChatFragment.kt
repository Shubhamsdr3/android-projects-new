package com.pandey.popcorn4.chat.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.pandey.popcorn4.AppConstants
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import com.pandey.popcorn4.chat.data.ImageMessage
import com.pandey.popcorn4.chat.data.MessageType
import com.pandey.popcorn4.chat.data.TextMessage
import com.pandey.popcorn4.chat.data.User
import com.pandey.popcorn4.chat.util.FireStoreUtil
import com.pandey.popcorn4.chat.util.StorageUtil
import com.pandey.popcorn4.customeviews.TitleTextToolbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat.*
import java.io.ByteArrayOutputStream
import java.util.*

class ChatFragment : BaseFragment<ChatFragment.ChatFragmentListener>() {

    private lateinit var titleTextToolbar : TitleTextToolbar

    private lateinit var messageRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var messageSection: Section

    private lateinit var currentChannelId : String

    private lateinit var currentUser: User

    private var otherUserId : String? = null

    companion object {

        private const val IMAGE_REQUEST_CODE = 1002

        fun newInstance(userName: String, userId: String) : ChatFragment {
            val chatFragment = ChatFragment()
            val args = Bundle()
            args.putString(AppConstants.USER_NAME, userName)
            args.putString(AppConstants.USER_ID, userId)
            chatFragment.arguments = args
            return chatFragment
        }
    }

    override fun initLayout() {
        titleTextToolbar = TitleTextToolbar(requireContext())
        titleTextToolbar.setTitle(arguments?.get(AppConstants.USER_NAME) as String?)

         FireStoreUtil.getCurrentUser {
             if (it != null) {
                 currentUser = it
             }
        }

       otherUserId = arguments?.getString(AppConstants.USER_ID).toString()

        if (otherUserId != null) {
            FireStoreUtil.getOrCreateChatChannel(otherUserId!!) { channelId ->
                currentChannelId = channelId
                messageRegistration = FireStoreUtil.addChatMessageListener(channelId, requireContext(), this::updateRecyclerView)

                // send text message when clicked on send
                send_image_btn.setOnClickListener {
                    val messageToSend = TextMessage(
                            et_chat_message.text.toString(),
                            Calendar.getInstance().time,
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            otherUserId!!,
                            currentUser.name
                    )
                    et_chat_message.setText("")
                    FireStoreUtil.sendMessage(messageToSend, channelId)
                }

                // send image messages
                fab_image.setOnClickListener {
                   val intent = Intent().apply {
                       type = "image/*"
                       action = Intent.ACTION_GET_CONTENT
                       putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                   }
                    startActivityForResult(Intent.createChooser(intent, "Select image"), IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (requestCode == IMAGE_REQUEST_CODE &&
               resultCode == Activity.RESULT_OK && data != null && data.data != null) {
           val selectedImageBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, data.data)
           val outputStream = ByteArrayOutputStream()
           selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
           val selectedImageBytes = outputStream.toByteArray()
           StorageUtil.uploadImageMessage(selectedImageBytes) { imagePath ->
               val messageToSend = ImageMessage(
                       imagePath,
                       Calendar.getInstance().time,
                       FirebaseAuth.getInstance().currentUser!!.uid,
                       otherUserId!!,
                       currentUser.name
               )
               FireStoreUtil.sendMessage(messageToSend, currentChannelId)
           }
       }
    }

    private fun updateRecyclerView(messages: List<Item<ViewHolder>>) {
        fun init() {
            chat_messages.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = GroupAdapter<ViewHolder>().apply {
                    messageSection = Section(messages)
                    this.add(messageSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messageSection.update(messages)

        if (shouldInitRecyclerView) {
            init()
        } else {
            updateItems()
        }
        // scroll to last position
        if (chat_messages != null) {
            chat_messages.adapter?.itemCount?.minus(1)?.let { chat_messages.scrollToPosition(it) }
        }
    }

    override fun initListeners() {

    }

    override fun getToolBar(): FrameLayout? {
        return titleTextToolbar
    }

    override fun getListenerClass(): Class<ChatFragmentListener> {
        return ChatFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_chat
    }

    interface ChatFragmentListener {

    }
}