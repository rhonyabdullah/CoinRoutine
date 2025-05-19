package com.learn.cmm.coins.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.learn.cmm.coins.presentation.component.PerformanceChart
import com.learn.cmm.theme.CoinRoutineTheme
import com.learn.cmm.theme.LocalCoinRoutineColorsPalette
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinsListScreen(
    onCoinClicked: (String) -> Unit
) {
    val viewModel = koinViewModel<CoinListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CoinsListContent(
        state = state,
        onCoinClicked = onCoinClicked,
        onDismissChart = viewModel::onDismissChart,
        onCoinLongPressed = viewModel::onCoinLongPressed,
    )
}

@Composable
private fun CoinsListContent(
    state: CoinState,
    onCoinClicked: (String) -> Unit,
    onDismissChart: () -> Unit,
    onCoinLongPressed: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.chartState != null) {
            CoinChartDialog(
                uiChartState = state.chartState,
                onDismiss = onDismissChart,
            )
        }
        CoinsList(
            coins = state.coins,
            onCoinLongPressed = onCoinLongPressed,
            onCoinClicked = onCoinClicked
        )
    }
}

@Composable
private fun CoinsList(
    coins: List<UiCoinListItem>,
    onCoinLongPressed: (String) -> Unit,
    onCoinClicked: (String) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(
                    text = "🔥 Top Coins:",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(coins) { coin ->
                CoinListItem(
                    coin = coin,
                    onCoinLongPressed = onCoinLongPressed,
                    onCoinClicked = onCoinClicked
                )
            }
        }
    }
}

@Composable
private fun CoinListItem(
    coin: UiCoinListItem,
    onCoinLongPressed: (String) -> Unit,
    onCoinClicked: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = { onCoinLongPressed(coin.id) },
                onClick = { onCoinClicked(coin.id) }
            )
            .padding(16.dp)
    ) {
        AsyncImage(
            model = coin.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(4.dp).clip(CircleShape).size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coin.name,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = coin.symbol,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = coin.formattedPrice,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = coin.formattedChange,
                color = if (coin.isPositive) LocalCoinRoutineColorsPalette.current.profitGreen else LocalCoinRoutineColorsPalette.current.lossRed,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            )
        }
    }
}

@Composable
private fun CoinChartDialog(
    uiChartState: UiChartState,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "24h Price chart for ${uiChartState.coinName}",
            )
        },
        text = {
            if (uiChartState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else {
                PerformanceChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    nodes = uiChartState.sparkLine,
                    profitColor = LocalCoinRoutineColorsPalette.current.profitGreen,
                    lossColor = LocalCoinRoutineColorsPalette.current.lossRed,
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(
                    text = "Close",
                )
            }
        }
    )
}

@Preview
@Composable
fun CoinsListContentPreview() {
    CoinRoutineTheme {
        CoinsListContent(
            state = CoinState(
                coins = listOf(
                    UiCoinListItem(
                        id = "bitcoin",
                        name = "Bitcoin",
                        symbol = "BTC",
                        iconUrl = "https://static.coinstats.app/coins/1650455588819.png",
                        formattedPrice = "$26,000",
                        formattedChange = "+1.5%",
                        isPositive = true
                    ),
                    UiCoinListItem(
                        id = "ethereum",
                        name = "Ethereum",
                        symbol = "ETH",
                        iconUrl = "https://static.coinstats.app/coins/1650455627820.png",
                        formattedPrice = "$1,800",
                        formattedChange = "-0.5%",
                        isPositive = false
                    ),
                    UiCoinListItem(
                        id = "tether",
                        name = "Tether",
                        symbol = "USDT",
                        iconUrl = "https://static.coinstats.app/coins/1650455658043.png",
                        formattedPrice = "$1.00",
                        formattedChange = "0.0%",
                        isPositive = true
                    )
                )
            ),
            onCoinClicked = {},
            onDismissChart = {},
            onCoinLongPressed = {}
        )
    }
}
