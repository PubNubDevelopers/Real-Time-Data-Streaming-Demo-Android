package com.pubnub.devrel.realtimestreaming.Messages

class MessageTwitter(name: String, id: String, message: String, source: String, country: String, location: String, followers: Long, timetoken: Long?) {
    val senderScreenName = name
    val senderId = id
    val messageText = message
    val messageSource = source
    val messageCountry = country
    val senderLocation = location
    val senderFollowerCount = followers
    val timetoken = timetoken
}