package com.pubnub.devrel.realtimestreaming.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pubnub.devrel.realtimestreaming.R
import com.pubnub.devrel.realtimestreaming.messages.MessageSensorNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun SensorNetworkScreen(messageListData : SnapshotStateList<MessageSensorNetwork>, messageListState : LazyListState,
                        coroutineScope: CoroutineScope, continueVisible: Boolean) {
    Column(modifier = Modifier.fillMaxSize())
    {
        if (continueVisible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp).clickable {
                        coroutineScope.launch {
                            if (messageListData.size > 1)
                                messageListState.scrollToItem(messageListData.size - 1)
                        }
                    }
            )
            {
                Text(
                    text = "Continue Scrolling...", style=MaterialTheme.typography.labelLarge, color=MaterialTheme.colorScheme.error
                )
            }
        }
        LazyColumn (
            state = messageListState,
            modifier = Modifier.fillMaxWidth().fillMaxHeight().background(MaterialTheme.colorScheme.surface),
            //contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            itemsIndexed(messageListData) {index, item ->
                val backgroundColor =  if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 10.dp))
                {
                    Image(
                        painter = painterResource(R.drawable.iot_icon),
                        contentDescription = "IoT Icon",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Text("Source: ", style= MaterialTheme.typography.titleMedium)
                    Text("Sensor Network (Simulated)", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Ambient Temperature: ", style= MaterialTheme.typography.titleMedium)
                    Text("${item.temperature}°c" , style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Humidity: ", style= MaterialTheme.typography.titleMedium)
                    Text("${item.humidity}%", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Photosensor: ", style= MaterialTheme.typography.titleMedium)
                    Text("${item.photosensor} w/m2", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Radiation Level: ", style= MaterialTheme.typography.titleMedium)
                    Text("${item.radiation} millirads/hr", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Sensor ID: ", style= MaterialTheme.typography.titleMedium)
                    Text(item.sensorId, style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 10.dp))
                {
                    Text("Timestamp: ", style= MaterialTheme.typography.titleMedium)
                    val sdf = SimpleDateFormat("yyyy MMMM dd HH:mm:ss")
                    val messageDate = item.timetoken?.let { Date(it/10000) }
                    Text(sdf.format(messageDate), style= MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }


}