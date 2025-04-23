package com.learn.cmm

import androidx.compose.runtime.Composable
import com.learn.cmm.presentation.CoinsListScreen
import com.learn.cmm.theme.CoinRoutineTheme

@Composable
fun App() {
    CoinRoutineTheme {
        CoinsListScreen {  }
    }
}
