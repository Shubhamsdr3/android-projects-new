package com.pandey.popcorn4.chat

import android.os.Bundle
import com.pandey.popcorn4.AppConstants
import com.pandey.popcorn4.base.BaseActivity
import com.pandey.popcorn4.chat.ui.ChatFragment

class ChatActivity : BaseActivity(), ChatFragment.ChatFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra(AppConstants.USER_NAME) && intent.hasExtra(AppConstants.USER_ID)) {
            startFragment(ChatFragment.newInstance(
                    intent.getStringExtra(AppConstants.USER_NAME),
                    intent.getStringExtra(AppConstants.USER_ID)
            ), false)
        }
    }

    override fun getLayoutResId(): Int {
       return 0
    }
}
