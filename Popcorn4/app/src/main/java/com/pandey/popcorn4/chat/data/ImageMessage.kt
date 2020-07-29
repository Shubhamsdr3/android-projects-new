package com.pandey.popcorn4.chat.data

import java.util.*

data class ImageMessage (
        val imagePath: String,
        override val time: Date,
        override val senderId: String,
        override val senderName: String,
        override val recipientId: String,
        override val type: String = MessageType.IMAGE

) : Message {
    constructor(): this("", Date(0), "", "", "")
}