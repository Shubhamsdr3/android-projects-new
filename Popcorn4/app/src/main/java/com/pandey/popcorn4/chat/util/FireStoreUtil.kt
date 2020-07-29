package com.pandey.popcorn4.chat.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.pandey.popcorn4.chat.adapter.ImageMessageAdapter
import com.pandey.popcorn4.chat.adapter.PersonItemAdapter
import com.pandey.popcorn4.chat.adapter.TextMessagesAdapter
import com.pandey.popcorn4.chat.data.*
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import timber.log.Timber

object FireStoreUtil {

    private val fireStoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef : DocumentReference
        get() = fireStoreInstance.document(
                "user/${FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException("UID is null")}"
        )

    private val chatChannelCollectionRef = fireStoreInstance.collection("chatChannel")

    fun sendMessage(message: Message, channelId: String) {
        chatChannelCollectionRef
                .document(channelId)
                .collection("messages")
                .add(message)
    }

    fun getOrCreateChatChannel(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels")
                .document(otherUserId).get().addOnSuccessListener {
                    if (it.exists()) {
                        onComplete(it["channelId"] as String)
                        return@addOnSuccessListener
                    }
                    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                    val newChannel = chatChannelCollectionRef.document()
                    newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                    currentUserDocRef.collection("engagedChatChannels")
                            .document(otherUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    fireStoreInstance.collection("user")
                            .document(otherUserId)
                            .collection("engagedChatChannels")
                            .document(currentUserId)
                            .set(mapOf("channelId" to newChannel.id))
                    onComplete(newChannel.id)
                }
    }

    fun addChatMessageListener(channelId: String, context: Context, onListen: (List<Item<ViewHolder>>) -> Unit) : ListenerRegistration {
        return chatChannelCollectionRef.document(channelId)
                .collection("messages")
                .orderBy("time")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Timber.e("ChatMessageListener error: ${firebaseFirestoreException.message}")
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Item<ViewHolder>>()
                    querySnapshot?.documents?.forEach {
                        if (it["type"] == MessageType.TEXT) {
                            items.add(TextMessagesAdapter(it.toObject(TextMessage::class.java)!!, context))
                        } else {
                            items.add(ImageMessageAdapter(it.toObject(ImageMessage::class.java)!!, context))
                        }
                    }
                    onListen(items)
                }
    }

    fun initUserFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            if (!it.exists()) {
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", "", null, mutableListOf())
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete() // higher order function
            }
        }
    }

    fun updateCurrentUser(name: String = "", bio : String = "", profilePicPath: String? = null) {
        val userFiledMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) {
            userFiledMap["name"] = name
        }
        if (bio.isNotBlank()) {
            userFiledMap["bio"] = bio
        }
        if (profilePicPath != null) {
            userFiledMap["profilePicturePath"] = profilePicPath
        }
        currentUserDocRef.update(userFiledMap)
    }

    fun getCurrentUser(onComplete: (User?) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            onComplete(it.toObject(User::class.java))
        }
    }

    fun addUserListener(context: Context, onListen: (List<Item<ViewHolder>>) -> Unit): ListenerRegistration {
        return fireStoreInstance.collection("user")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Timber.e(firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val  items = mutableListOf<Item<ViewHolder>>()
                    querySnapshot?.documents?.forEach {
                        if (it.id != FirebaseAuth.getInstance().currentUser?.uid) {
                            items.add(PersonItemAdapter(
                                   it.toObject(User::class.java)!!,
                                    it.id,
                                    context
                            ))
                            onListen(items)
                        }
                    }
                }
    }

    fun removeRegistrationListener(registration: ListenerRegistration) = registration.remove()

    //FCM: begin
    fun getFCMRegistrationToken(onComplete: (tokens: MutableList<String>) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            onComplete(user!!.registrationTokens)
        }
    }

    fun setFCMRegistrationToken(registrationTokens: MutableList<String>) {
        currentUserDocRef.update(mapOf("registrationTokens" to registrationTokens))
    }
    //FCM end
}