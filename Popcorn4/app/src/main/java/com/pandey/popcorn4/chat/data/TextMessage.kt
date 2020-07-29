package com.pandey.popcorn4.chat.data

import java.util.*

data class TextMessage (
        val text: String,
        override val time: Date,
        override val senderId: String,
        override val senderName: String,
        override val recipientId: String,
        override val type: String = MessageType.TEXT
) : Message {
    constructor(): this("", Date(0), "", "", "")
}