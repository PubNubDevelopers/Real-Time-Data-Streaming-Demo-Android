package com.pubnub.devrel.realtimestreaming.Messages

class MessageMarketOrders(stock: String, bidPrice: Double, orderQuantity: Int, tradeType: String, timetoken: Long?) {
    val stock = stock
    val bidPrice = bidPrice
    val orderQuantity = orderQuantity
    val tradeType = tradeType
    val timetoken = timetoken
}