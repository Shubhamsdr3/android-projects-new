package com.pandey.popcorn4.chat.adapter

import android.content.Context
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.data.ImageMessage
import com.pandey.popcorn4.chat.glide.GlideApp
import com.pandey.popcorn4.chat.util.StorageUtil
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.image_message_item.view.*

class ImageMessageAdapter(private val message: ImageMessage, private val context: Context) : BaseAdapter(message) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)

        // load image from firestore
        GlideApp.with(context)
                .load(StorageUtil.pathToReference(message.imagePath))
                .placeholder(R.drawable.ic_image_black)
                .into(viewHolder.itemView.image_message)

    }

    override fun isSameAs(other: Item<*>?): Boolean {
        if (other !is ImageMessageAdapter) {
            return false
        }
        if (this.message != other.message) {
            return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? ImageMessageAdapter)
    }

    override fun getLayout(): Int {
        return R.layout.image_message_item
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }

}