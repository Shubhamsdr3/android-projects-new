package com.pandey.popcorn4.chat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.iid.FirebaseInstanceId
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.services.FBNotificationService
import com.pandey.popcorn4.chat.util.FireStoreUtil
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import timber.log.Timber

class SigningActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 1

    private val singINProviders
            = listOf(AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true)
            .setRequireName(true)
            .build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        sing_in_button.setOnClickListener {
            val intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(singINProviders)
                    .setIsSmartLockEnabled(false)
                    .build()
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == RC_SIGN_IN) {
                val response = IdpResponse.fromResultIntent(data)
                if (resultCode == Activity.RESULT_OK) {
                    val progressDialog = indeterminateProgressDialog("Setting up your account")
                    FireStoreUtil.initUserFirstTime {
                        startActivity(intentFor<ChatHomeActivity>().newTask().clearTask())

                        FirebaseInstanceId.getInstance().instanceId
                                .addOnCompleteListener {
                                    if (!it.isSuccessful) {
                                        Timber.e(it.exception)
                                        return@addOnCompleteListener
                                    }
                                    val newRegistrationToken = it.result?.token
                                    FBNotificationService.addTokenToFirestore(newRegistrationToken)
                                }
                        progressDialog.dismiss()
                    }
                 }
                else if (resultCode == Activity.RESULT_CANCELED) {
                    if (response == null) return
                    when (response.error?.errorCode) {
                        ErrorCodes.NO_NETWORK -> Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show()
                        ErrorCodes.UNKNOWN_ERROR -> Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}
