package com.pandey.popcorn4.chat.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pandey.popcorn4.chat.util.FireStoreUtil
import com.pandey.popcorn4.notifications.NotificationManager
import timber.log.Timber

open class FBNotificationService : FirebaseMessagingService() {

    companion object {
        fun addTokenToFirestore(newRegistrationToken: String?) {
            if (newRegistrationToken == null) throw NullPointerException("FCM token is null")
            FireStoreUtil.getFCMRegistrationToken { tokens ->
                if (tokens.contains(newRegistrationToken)) { // alredy exist
                    return@getFCMRegistrationToken
                }
                // save to firestore
                tokens.add(newRegistrationToken)
                FireStoreUtil.setFCMRegistrationToken(tokens)
            }
        }
    }

    override fun onNewToken(p0: String) {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Timber.e(it.exception)
                        return@addOnCompleteListener
                    }
                    val newRegistrationToken = it.result?.token
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        addTokenToFirestore(newRegistrationToken)
                    }
                }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d("The received message is: %s", remoteMessage.toString())
        val mNotificationManager = NotificationManager(applicationContext)
        mNotificationManager.notifyRemoteMessage(remoteMessage)

//        if (remoteMessage.notification != null) {
//            Timber.d("FCM message receivedd")
//            //TODO: show notification
//        }
    }
}