package com.pandey.popcorn4.chat.adapter

import android.view.Gravity
import android.widget.FrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.data.Message
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_text_message.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseAdapter(private val message: Message) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.msg_sent_time.text = getDateTimeString(message.time)
        setMessageGravity(viewHolder)
    }

    private fun getDateTimeString(dateTime: Date) : String {
        val dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        return dateFormat.format(dateTime)
    }

    private fun setMessageGravity(viewHolder: ViewHolder) {
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
            // If I sent the message
            viewHolder.itemView.message_root.apply {
                backgroundResource = R.drawable.rounded_corner_green_bg
                val layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)
//                layoutParams.setMargins(60, 0, 0, 0)
                this.layoutParams = layoutParams
            }
        } else {
            //if the other user sent the message
            viewHolder.itemView.message_root.apply {
                backgroundResource = R.drawable.rounded_corner_light_white_bg
                val layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
//                layoutParams.setMargins(0, 0, 60, 0)
                this.layoutParams = layoutParams
            }
        }
    }
}