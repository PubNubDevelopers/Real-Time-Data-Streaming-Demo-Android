package com.pubnub.devrel.realtimestreaming.Messages

class MessageGameState(actionName: String, actionType: String, actionValue: Int, coordX: Int, coordY: Int, timetoken: Long?) {
    val actionName = actionName
    val actionType = actionType
    val actionValue = actionValue
    val coord_X = coordX
    val coord_Y = coordY
    val timetoken = timetoken
}