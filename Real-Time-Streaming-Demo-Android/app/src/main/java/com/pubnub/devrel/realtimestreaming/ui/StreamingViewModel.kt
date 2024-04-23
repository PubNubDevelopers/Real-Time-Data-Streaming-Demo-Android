package com.pubnub.devrel.realtimestreaming.ui

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.v2.subscriptions.Subscription
import com.pubnub.devrel.realtimestreaming.Messages.MessageGameState
import com.pubnub.devrel.realtimestreaming.Messages.MessageMarketOrders
import com.pubnub.devrel.realtimestreaming.Messages.MessageSensorNetwork
import com.pubnub.devrel.realtimestreaming.Messages.MessageTwitter
import com.pubnub.devrel.realtimestreaming.Messages.MessageWikipedia
import com.pubnub.devrel.realtimestreaming.RealTimeStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class StreamingViewModel : ViewModel() {

    val LOG_TAG = "PNRealTime"
    private var currentStream: RealTimeStream = RealTimeStream.Start
    val messageListDataTwitter = mutableStateListOf<MessageTwitter>()
    val messageListDataWikipedia = mutableStateListOf<MessageWikipedia>()
    val messageListDataGameState = mutableStateListOf<MessageGameState>()
    val messageListDataSensorNetwork = mutableStateListOf<MessageSensorNetwork>()
    val messageListDataMarketOrders = mutableStateListOf<MessageMarketOrders>()

    private val channelSubscriptions = LinkedHashMap<RealTimeStream, Subscription>()

    lateinit var messageListState: LazyListState
    var continueVisible by mutableStateOf(false)
    private var newScreen = false

    fun setActiveStream(stream: RealTimeStream) {
        currentStream = stream
        newScreen = true
        if (currentStream == RealTimeStream.Start) {
            cancelStreams()
        }
        else
        {
            channelSubscriptions[currentStream]?.subscribe()
        }
    }

    private fun cancelStreams()
    {
        for ((key, value) in channelSubscriptions) {
            value.unsubscribe()
        }
    }

    fun messageReceived(pnMessageResult: PNMessageResult) {
        //Log.d(LOG_TAG, "Received message ${pnMessageResult.message}")
        var scrollList = true
        if (!newScreen && messageListState.canScrollForward) {
            scrollList = false
            continueVisible = true
        }
        else
        {
            newScreen = false
            continueVisible = false
        }
        //  Stream specific parsing of the response
        if (currentStream == RealTimeStream.TwitterX)
        {
            val message = JSONObject(pnMessageResult.message.toString())
            val user = JSONObject(message.get("user").toString())
            var place = JSONObject(message.get("place").toString())
            val twitterMessage = MessageTwitter(user.getString("screen_name"),
                message.getString("id_str"), message.getString("text"),
                message.getString("source").replace("<a .*\">".toRegex(), "").replace("</a>", ""), place.getString("country"),
                user.getString("location"),user.getLong("followers_count"), pnMessageResult.timetoken)
            messageListDataTwitter.add(twitterMessage)
            if (scrollList)
            {
                CoroutineScope(Dispatchers.Main).launch {
                    messageListState.scrollToItem(messageListDataTwitter.size - 1)
                }
            }
        }
        else if (currentStream == RealTimeStream.Wikipedia)
        {
            Log.d(LOG_TAG, "Received Wikipedia")
            val message = JSONObject(pnMessageResult.message.toString())
            val wikipediaMessage = MessageWikipedia(message.getString("event"),
                message.getString("item"), message.getString("link"),
                message.getString("user"), pnMessageResult.timetoken)
            messageListDataWikipedia.add(wikipediaMessage)
            if (scrollList)
            {
                CoroutineScope(Dispatchers.Main).launch {
                    messageListState.scrollToItem(messageListDataWikipedia.size - 1)
                }
            }
        }
        else if (currentStream == RealTimeStream.GameState)
        {
            val message = JSONObject(pnMessageResult.message.toString())
            val gameStateMessage = MessageGameState(message.getString("action_name"),
                message.getString("action_type"), message.getInt("action_value"),
                message.getInt("coord_x"), message.getInt("coord_y"),
                pnMessageResult.timetoken)
            messageListDataGameState.add(gameStateMessage)
            if (scrollList)
            {
                CoroutineScope(Dispatchers.Main).launch {
                    messageListState.scrollToItem(messageListDataGameState.size - 1)
                }
            }
        }
        else if (currentStream == RealTimeStream.SensorNetwork)
        {
            val message = JSONObject(pnMessageResult.message.toString())
            val sensorNetworkMessage = MessageSensorNetwork(message.getDouble("ambient_temperature"),
                message.getDouble("humidity"), message.getInt("photosensor"),
                message.getDouble("radiation_level"), message.getString("sensor_uuid"),
                pnMessageResult.timetoken)
            messageListDataSensorNetwork.add(sensorNetworkMessage)
            if (scrollList)
            {
                CoroutineScope(Dispatchers.Main).launch {
                    messageListState.scrollToItem(messageListDataSensorNetwork.size - 1)
                }
            }
        }
        else if (currentStream == RealTimeStream.MarketOrders)
        {
            val message = JSONObject(pnMessageResult.message.toString())
            val marketOrdersMessage = MessageMarketOrders(message.getString("symbol"),
                message.getDouble("bid_price"), message.getInt("order_quantity"),
                message.getString("trade_type"),
                pnMessageResult.timetoken)
            messageListDataMarketOrders.add(marketOrdersMessage)
            if (scrollList)
            {
                CoroutineScope(Dispatchers.Main).launch {
                    messageListState.scrollToItem(messageListDataMarketOrders.size - 1)
                }
            }
        }
    }

    fun setSubscription(stream: RealTimeStream, channelSubscription: Subscription) {
        channelSubscriptions[stream] = channelSubscription
    }

}