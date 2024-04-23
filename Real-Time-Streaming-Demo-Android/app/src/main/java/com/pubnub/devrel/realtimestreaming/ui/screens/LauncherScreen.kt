package com.pubnub.devrel.realtimestreaming.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pubnub.devrel.realtimestreaming.R

@Composable
fun LauncherScreen(onTwitterButtonClicked: () -> Unit,
                   onWikipediaButtonClicked: () -> Unit,
                   onGameStateButtonClicked: () -> Unit,
                   onSensorNetworkButtonClicked: () -> Unit,
                   onMarketOrdersButtonClicked: () -> Unit){
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(end=5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal=30.dp, vertical = 20.dp).heightIn(max=50.dp), horizontalArrangement = Arrangement.Center)
        {
            Image(
                painter = painterResource(id = R.drawable.pubnub_logo__red_),
                contentDescription = "PubNub Logo",
                contentScale = ContentScale.Fit
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.Center)
        {
            Text("Real-Time Streaming Demo", textAlign= TextAlign.Center, style= MaterialTheme.typography.headlineLarge)
        }
        Row(modifier = Modifier.fillMaxWidth().clickable {
            onTwitterButtonClicked()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(id = R.drawable.social_icon),
                contentDescription = "Social Icon",
                modifier = Modifier.width(90.dp).padding(horizontal = 10.dp)
            )
            Text(
                text = "Twitter (X) Stream", style=MaterialTheme.typography.headlineSmall
            )
        }
        Row(modifier = Modifier.fillMaxWidth().clickable {
            onWikipediaButtonClicked()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(id = R.drawable.web3_icon),
                contentDescription = "Web3 Icon",
                modifier = Modifier.width(90.dp).padding(horizontal = 10.dp)
            )
            Text(
                text = "Wikipedia Changes (Live)", style=MaterialTheme.typography.headlineSmall
            )
        }
        Row(modifier = Modifier.fillMaxWidth().clickable {
            onGameStateButtonClicked()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(id = R.drawable.gaming_icon),
                contentDescription = "Gaming Icon",
                modifier = Modifier.width(90.dp).padding(horizontal = 10.dp)
            )
            Text(
                text = "Game State (Simulated)", style=MaterialTheme.typography.headlineSmall
            )
        }
        Row(modifier = Modifier.fillMaxWidth().clickable {
            onSensorNetworkButtonClicked()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(id = R.drawable.iot_icon),
                contentDescription = "IoT Icon",
                modifier = Modifier.width(90.dp).padding(horizontal = 10.dp)
            )
            Text(
                text = "Sensor Network (Simulated)", style=MaterialTheme.typography.headlineSmall
            )
        }
        Row(modifier = Modifier.fillMaxWidth().clickable {
            onMarketOrdersButtonClicked()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            Image(
                painter = painterResource(id = R.drawable.regulations_icon),
                contentDescription = "Regulations Icon",
                modifier = Modifier.width(90.dp).padding(horizontal = 10.dp)
            )
            Text(
                text = "Market Orders (Simulated)", style=MaterialTheme.typography.headlineSmall
            )
        }
        /*
        Text(
            text = "I am the launcher"
        )
        ClickableText(text = AnnotatedString("Launch Twitter screen"), onClick = {
            onTwitterButtonClicked()
        })
        ClickableText(text = AnnotatedString("Launch Wikipedia screen"), onClick = {
            onWikipediaButtonClicked()
        })
        ClickableText(text = AnnotatedString("Launch Game State Screen"), onClick = {
            onGameStateButtonClicked()
        })
        ClickableText(text = AnnotatedString("Launch Sensor Network Screen"), onClick = {
            onSensorNetworkButtonClicked()
        })
        ClickableText(text = AnnotatedString("Launch Market Orders Screen"), onClick = {
            onMarketOrdersButtonClicked()
        })*/
    }

}
