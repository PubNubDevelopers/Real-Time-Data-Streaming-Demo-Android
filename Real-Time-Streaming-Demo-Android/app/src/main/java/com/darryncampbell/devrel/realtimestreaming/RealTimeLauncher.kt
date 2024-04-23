package com.darryncampbell.devrel.realtimestreaming
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darryncampbell.devrel.realtimestreaming.ui.StreamingViewModel
import com.darryncampbell.devrel.realtimestreaming.ui.screens.GameStateScreen
import com.darryncampbell.devrel.realtimestreaming.ui.screens.LauncherScreen
import com.darryncampbell.devrel.realtimestreaming.ui.screens.MarketOrdersScreen
import com.darryncampbell.devrel.realtimestreaming.ui.screens.SensorNetworkScreen
import com.darryncampbell.devrel.realtimestreaming.ui.screens.TwitterScreen
import com.darryncampbell.devrel.realtimestreaming.ui.screens.WikipediaScreen
import kotlinx.coroutines.CoroutineScope

private lateinit var coroutineScope: CoroutineScope

/**
 * enum values that represent the screens in the app
 */
enum class RealTimeStream(@StringRes val title: Int, val icon: Int) {
    Start(title = R.string.launcher, icon = R.drawable.pubsub_icon),
    TwitterX(title = R.string.twitter, icon = R.drawable.social_icon),
    Wikipedia(title = R.string.wikipedia, icon = R.drawable.web3_icon),
    GameState(title = R.string.game_state, icon = R.drawable.gaming_icon),
    SensorNetwork(title = R.string.sensor_network, icon = R.drawable.iot_icon),
    MarketOrders(title = R.string.market_orders, icon = R.drawable.regulations_icon)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealTimeAppBar(
    currentScreen: RealTimeStream,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Row {Text(stringResource(currentScreen.title)) }},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        actions = {
          IconButton(onClick={}) {
              Icon(imageVector = ImageVector.vectorResource(currentScreen.icon), contentDescription = "")
          }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun RealTimeApp(
    viewModel: StreamingViewModel,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = RealTimeStream.valueOf(
        backStackEntry?.destination?.route ?: RealTimeStream.Start.name
    )
    if (currentScreen == RealTimeStream.Start)
    {
        viewModel.setActiveStream(RealTimeStream.Start);
    }

    Scaffold(
        topBar = {
            RealTimeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp(); }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = RealTimeStream.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = RealTimeStream.Start.name) {
                LauncherScreen(
                    onTwitterButtonClicked = {
                        navController.navigate(RealTimeStream.TwitterX.name)
                        viewModel.setActiveStream(RealTimeStream.TwitterX)
                    },
                    onWikipediaButtonClicked = {
                        navController.navigate(RealTimeStream.Wikipedia.name)
                        viewModel.setActiveStream(RealTimeStream.Wikipedia)
                    },
                    onGameStateButtonClicked = {
                        navController.navigate(RealTimeStream.GameState.name)
                        viewModel.setActiveStream(RealTimeStream.GameState)
                    },
                    onSensorNetworkButtonClicked = {
                        navController.navigate(RealTimeStream.SensorNetwork.name)
                        viewModel.setActiveStream(RealTimeStream.SensorNetwork)
                    },
                    onMarketOrdersButtonClicked = {
                        navController.navigate(RealTimeStream.MarketOrders.name)
                        viewModel.setActiveStream(RealTimeStream.MarketOrders)
                    }
                )
            }
            composable(route = RealTimeStream.TwitterX.name) {
                viewModel.messageListState = rememberLazyListState()
                coroutineScope = rememberCoroutineScope()
                TwitterScreen(viewModel.messageListDataTwitter, viewModel.messageListState, coroutineScope, viewModel.continueVisible
                )
            }
            composable(route = RealTimeStream.Wikipedia.name) {
                viewModel.messageListState = rememberLazyListState()
                coroutineScope = rememberCoroutineScope()
                WikipediaScreen(viewModel.messageListDataWikipedia, viewModel.messageListState, coroutineScope, viewModel.continueVisible
                )
            }
            composable(route = RealTimeStream.GameState.name) {
                viewModel.messageListState = rememberLazyListState()
                coroutineScope = rememberCoroutineScope()
                GameStateScreen(viewModel.messageListDataGameState, viewModel.messageListState, coroutineScope, viewModel.continueVisible
                )
            }
            composable(route = RealTimeStream.SensorNetwork.name) {
                viewModel.messageListState = rememberLazyListState()
                coroutineScope = rememberCoroutineScope()
                SensorNetworkScreen(viewModel.messageListDataSensorNetwork, viewModel.messageListState, coroutineScope, viewModel.continueVisible
                )
            }
            composable(route = RealTimeStream.MarketOrders.name) {
                viewModel.messageListState = rememberLazyListState()
                coroutineScope = rememberCoroutineScope()
                MarketOrdersScreen(viewModel.messageListDataMarketOrders, viewModel.messageListState, coroutineScope, viewModel.continueVisible
                )
            }
        }
    }
}