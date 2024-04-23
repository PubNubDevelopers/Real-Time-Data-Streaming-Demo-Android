package com.darryncampbell.devrel.realtimestreaming.messages

class MessageGameState(val actionName: String,
                       val actionType: String,
                       val actionValue: Int, val coordX: Int, val coordY: Int, val timetoken: Long?
) {
}