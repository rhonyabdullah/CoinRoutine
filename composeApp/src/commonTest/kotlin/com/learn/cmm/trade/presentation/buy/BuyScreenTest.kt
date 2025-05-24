package com.learn.cmm.trade.presentation.buy

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import coinroutine.composeapp.generated.resources.Res
import coinroutine.composeapp.generated.resources.error_unknown
import com.learn.cmm.trade.presentation.common.TradeScreen
import com.learn.cmm.trade.presentation.common.TradeState
import com.learn.cmm.trade.presentation.common.TradeType
import com.learn.cmm.trade.presentation.common.UiTradeCoinItem
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class BuyScreenTest {

    lateinit var buyNow: String
    lateinit var sellNow: String

    @BeforeTest
    fun setUp() {
        buyNow = "Buy Now"
        sellNow = "Sell Now"
    }

    @Test
    fun checkSubmitButtonLabelChangesWithTradeType() = runComposeUiTest {
        val state = TradeState(
            coin = UiTradeCoinItem(
                id = "BTC_ID",
                name = "Bitcoin",
                symbol = "BTC",
                iconUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                price = 50000.0
            )
        )

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.BUY,
                onAmountChange = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithTag("submit_button").assertExists()
        onNodeWithText(buyNow).assertIsDisplayed()

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.SELL,
                onAmountChange = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithTag("submit_button").assertExists()
        onNodeWithText(sellNow).assertIsDisplayed()
    }

    @Test
    fun checkIfCoinNameShownProperlyInBuy() = runComposeUiTest {
        val state = TradeState(
            coin = UiTradeCoinItem(
                id = "BTC_ID",
                name = "Bitcoin",
                symbol = "BTC",
                iconUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                price = 50000.0
            )
        )
        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.BUY,
                onAmountChange = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithTag("trade_screen_coin_name").assertExists()
        onNodeWithTag("trade_screen_coin_name").assertTextEquals("Bitcoin")
    }

    @Test
    fun checkErrorIsShownProperly() = runComposeUiTest {
        val state = TradeState(
            coin = UiTradeCoinItem(
                id = "BTC_ID",
                name = "Bitcoin",
                symbol = "BTC",
                iconUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                price = 50000.0
            ),
            error = Res.string.error_unknown
        )

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.BUY,
                onAmountChange = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithTag("trade_error").assertExists()
        onNodeWithTag("trade_error").assertIsDisplayed()
    }
}
