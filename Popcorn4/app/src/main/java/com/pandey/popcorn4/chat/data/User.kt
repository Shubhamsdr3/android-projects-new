package com.pandey.popcorn4.chat.data

data class User(
        val name: String,
        val bio: String,
        val profilePicturePath: String?,
        val registrationTokens: MutableList<String>
) {
    constructor(): this("", "", null, mutableListOf())
}