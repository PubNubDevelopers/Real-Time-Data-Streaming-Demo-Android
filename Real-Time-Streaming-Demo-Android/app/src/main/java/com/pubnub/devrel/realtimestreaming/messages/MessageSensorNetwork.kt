package com.pubnub.devrel.realtimestreaming.messages

class MessageSensorNetwork(val temperature: Double,
                           val humidity: Double,
                           val photosensor: Int, val radiation: Double, val sensorId: String,
                           val timetoken: Long?
) {
}