package com.pubnub.devrel.realtimestreaming.messages

class MessageWikipedia(val eventType: String,
                       val changedItem: String, val link: String, val username: String,
                       val timetoken: Long?
)