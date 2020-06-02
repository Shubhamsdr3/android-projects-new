package com.pandey.popcorn4.chat.adapter

import android.content.Context
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.data.User
import com.pandey.popcorn4.chat.glide.GlideApp
import com.pandey.popcorn4.chat.util.StorageUtil
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.person_item_view.view.*

class PersonItemAdapter(val person: User, val userId: String, private val context: Context) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.person_item_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.user_name.text = person.name
        viewHolder.itemView.user_bio.text = person.bio
        if (person.profilePicturePath != null) {
            GlideApp.with(context).load(StorageUtil.pathToReference(person.profilePicturePath))
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .into(viewHolder.itemView.user_profile)
        }
    }
}