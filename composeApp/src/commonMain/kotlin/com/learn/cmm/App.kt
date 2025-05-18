package com.learn.cmm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learn.cmm.coins.presentation.CoinsListScreen
import com.learn.cmm.core.navigation.Buy
import com.learn.cmm.core.navigation.Coins
import com.learn.cmm.core.navigation.Portfolio
import com.learn.cmm.core.navigation.Sell
import com.learn.cmm.portofolio.presentation.PortfolioScreen
import com.learn.cmm.theme.CoinRoutineTheme
import com.learn.cmm.trade.presentation.buy.BuyScreen
import com.learn.cmm.trade.presentation.sell.SellScreen

@Composable
fun App() {
    val navController: NavHostController = rememberNavController()
    CoinRoutineTheme {
        NavHost(
            navController = navController,
            startDestination = Portfolio,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<Portfolio> {
                PortfolioScreen(
                    onCoinItemClicked = { coinId -> // TODO: will be used later
                        navController.navigate(Sell)
                    },
                    onDiscoverCoinsClicked = {
                        navController.navigate(Coins)
                    }
                )
            }

            composable<Coins> {
                CoinsListScreen { coinId -> // TODO: will be used later
                    navController.navigate(Buy)
                }
            }

            composable<Buy> { navBackStackEntry ->
                BuyScreen(
                    coinId = "todo",
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }
            composable<Sell> { navBackStackEntry ->
                SellScreen(
                    coinId = "todo",
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }

        }
    }
}
