package com.pubnub.devrel.realtimestreaming.Messages

class MessageSensorNetwork(temperature: Double, humidity: Double, photosensor: Int, radiation: Double, sensorId: String, timetoken: Long?) {
    val temperature = temperature
    val humidity = humidity
    val photosensor = photosensor
    val radiation = radiation
    val sensorId = sensorId
    val timetoken = timetoken
}