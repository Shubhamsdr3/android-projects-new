package com.pandey.popcorn4.chat.data

data class ChatChannel(val userIds : MutableList<String>) {
    constructor(): this(mutableListOf())
}