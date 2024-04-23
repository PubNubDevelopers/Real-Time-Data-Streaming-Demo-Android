package com.pubnub.devrel.realtimestreaming.Messages

class MessageWikipedia(eventType: String, changedItem: String, link: String, username: String, timetoken: Long?) {
    val eventType = eventType
    val changedItem = changedItem
    val link = link
    val username = username
    val timetoken = timetoken
}