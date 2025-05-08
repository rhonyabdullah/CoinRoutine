package com.learn.cmm

import androidx.compose.runtime.Composable
import com.learn.cmm.portofolio.presentation.PortfolioScreen
import com.learn.cmm.theme.CoinRoutineTheme

@Composable
fun App() {
    CoinRoutineTheme {
//        CoinsListScreen {  }
        PortfolioScreen(
            onCoinItemClicked = {},
            onDiscoverCoinsClicked = {}
        )
    }
}
