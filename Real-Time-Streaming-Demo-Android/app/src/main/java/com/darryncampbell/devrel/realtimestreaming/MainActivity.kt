package com.darryncampbell.devrel.realtimestreaming

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.darryncampbell.devrel.realtimestreaming.ui.StreamingViewModel
import com.darryncampbell.devrel.realtimestreaming.ui.theme.RealTimeStreamingTheme
import com.pubnub.api.PubNub
import com.pubnub.api.UserId
import com.pubnub.api.callbacks.Listener
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.v2.PNConfiguration

class MainActivity : ComponentActivity() {

    val LOG_TAG = "PNRealTime"
    private val SUBSCRIBE_KEY_TWITTER = "sub-c-d00e0d32-66ac-4628-aa65-a42a1f0c493b"
    private val SUBSCRIBE_KEY_WIKIPEDIA = "sub-c-83a959c1-2a4f-481b-8eb0-eab514c06ebf"
    private val SUBSCRIBE_KEY_SIMULATORS = "sub-c-99084bc5-1844-4e1c-82ca-a01b18166ca8"
    val CHANNEL_TWITTER = "pubnub-twitter"
    val CHANNEL_WIKIPEDIA = "pubnub-wikipedia"
    val CHANNEL_GAME_STATE = "pubnub-game-state"
    val CHANNEL_SENSOR_NETWORK = "pubnub-sensor-network"
    val CHANNEL_MARKET_ORDERS = "pubnub-market-orders"
    private lateinit var deviceId: String
    private lateinit var pubnub1: PubNub
    private lateinit var pubnub2: PubNub
    private lateinit var pubnub3: PubNub
    private lateinit var mListener: Listener
    var viewModel = StreamingViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        //  Create a device-specific DeviceId to represent this device and user, so PubNub knows who is connecting.
        //  More info: https://support.pubnub.com/hc/en-us/articles/360051496532-How-do-I-set-the-UUID-
        //  All Android IDs are user-resettable but are still appropriate for use here.
        deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        //  Create PubNub configuration and instantiate the PubNub object, used to communicate with PubNub
        val config1 = PNConfiguration.builder(UserId(deviceId),
            SUBSCRIBE_KEY_TWITTER).build()
        val config2 = PNConfiguration.builder(UserId(deviceId),
            SUBSCRIBE_KEY_WIKIPEDIA).build()
        val config3 = PNConfiguration.builder(UserId(deviceId),
            SUBSCRIBE_KEY_SIMULATORS).build()

        //  There are actually 3 PubNub objects since the streams are on different sub keys
        pubnub1 = PubNub.create(config1)
        pubnub2 = PubNub.create(config2)
        pubnub3 = PubNub.create(config3)
        //  Using Event Listeners
        val twitterSubscription = pubnub1.channel(CHANNEL_TWITTER).subscription()
        val wikipediaSubscription = pubnub2.channel(CHANNEL_WIKIPEDIA).subscription()
        val gameStateSubscription = pubnub3.channel(CHANNEL_GAME_STATE).subscription()
        val sensorNetworkSubscription = pubnub3.channel(CHANNEL_SENSOR_NETWORK).subscription()
        val marketOrdersSubscription = pubnub3.channel(CHANNEL_MARKET_ORDERS).subscription()
        viewModel.setSubscription(RealTimeStream.TwitterX, twitterSubscription)
        viewModel.setSubscription(RealTimeStream.Wikipedia, wikipediaSubscription)
        viewModel.setSubscription(RealTimeStream.GameState, gameStateSubscription)
        viewModel.setSubscription(RealTimeStream.SensorNetwork, sensorNetworkSubscription)
        viewModel.setSubscription(RealTimeStream.MarketOrders, marketOrdersSubscription)
        setContent {
            RealTimeStreamingTheme {
                RealTimeApp(viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val listener = object : SubscribeCallback() {
            override fun status(pubnub: PubNub, status: PNStatus) {
                //  Not used
                Log.d(LOG_TAG, "Status Received")
            }

            override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
                //Log.d(LOG_TAG, "Received message ${pnMessageResult.message}")
                viewModel.messageReceived(pnMessageResult)
            }
        }

        pubnub1.addListener(listener)
        pubnub2.addListener(listener)
        pubnub3.addListener(listener)
        mListener = listener
    }

    override fun onPause() {
        super.onPause()
        pubnub1.removeListener(mListener)
        pubnub2.removeListener(mListener)
        pubnub3.removeListener(mListener)
    }
}