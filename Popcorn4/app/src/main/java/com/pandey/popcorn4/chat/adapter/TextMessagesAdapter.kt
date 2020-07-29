package com.pandey.popcorn4.chat.adapter

import android.content.Context
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.data.TextMessage
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_text_message.view.*

class TextMessagesAdapter(val message: TextMessage, val context: Context) : BaseAdapter(message) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_message.text = message.text
        super.bind(viewHolder, position)
    }

    override fun isSameAs(other: Item<*>?): Boolean {
        if (other !is TextMessagesAdapter) {
            return false
        }
        if (this.message != other.message) {
            return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? TextMessagesAdapter)
    }

    override fun getLayout(): Int {
        return  R.layout.item_text_message
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }
}