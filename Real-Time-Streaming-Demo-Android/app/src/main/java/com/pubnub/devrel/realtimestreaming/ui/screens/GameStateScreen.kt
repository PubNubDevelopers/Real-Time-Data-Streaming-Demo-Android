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
import com.pubnub.devrel.realtimestreaming.Messages.MessageGameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun GameStateScreen(messageListData : SnapshotStateList<MessageGameState>, messageListState : LazyListState,
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
                        painter = painterResource(R.drawable.gaming_icon),
                        contentDescription = "Gaming Icon",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Text("Source: ", style= MaterialTheme.typography.titleMedium)
                    Text("Game State (Simulated)", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Action Name: ", style= MaterialTheme.typography.titleMedium)
                    Text(item.actionName, style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Action Type: ", style= MaterialTheme.typography.titleMedium)
                    Text(item.actionType, style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Action Value: ", style= MaterialTheme.typography.titleMedium)
                    Text(item.actionValue.toString(), style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 2.dp))
                {
                    Text("Coordinates: ", style= MaterialTheme.typography.titleMedium)
                    Text("x = ${item.coord_X}, y = ${item.coord_Y}", style= MaterialTheme.typography.bodyLarge)
                }
                Row (modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 10.dp))
                {
                    Text("Timestamp: ", style= MaterialTheme.typography.titleMedium)
                    val sdf = SimpleDateFormat("yyyy MM dd HH:mm:ss")
                    val messageDate = item.timetoken?.let { Date(it/10000) }
                    Text(sdf.format(messageDate), style= MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }


}