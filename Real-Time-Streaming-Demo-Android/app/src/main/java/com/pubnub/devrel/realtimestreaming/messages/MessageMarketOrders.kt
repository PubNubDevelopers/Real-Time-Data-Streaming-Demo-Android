package com.pubnub.devrel.realtimestreaming.messages

class MessageMarketOrders(val stock: String, val bidPrice: Double,
                          val orderQuantity: Int, val tradeType: String, val timetoken: Long?
)