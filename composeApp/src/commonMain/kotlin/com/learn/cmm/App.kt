package com.learn.cmm

import androidx.compose.runtime.Composable
import com.learn.cmm.portofolio.presentation.PortfolioScreen
import com.learn.cmm.presentation.CoinsListScreen
import com.learn.cmm.theme.CoinRoutineTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
