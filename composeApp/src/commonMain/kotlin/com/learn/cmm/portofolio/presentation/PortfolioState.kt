package com.learn.cmm.portofolio.presentation

import org.jetbrains.compose.resources.StringResource

data class PortfolioState(
    val portfolioValue: String = "",
    val cashBalance: String = "",
    val showBuyButton: Boolean = false,
    val isLoading: Boolean = false,
    val error: StringResource? = null,
    val coins: List<UiPortfolioCoinItem> = emptyList(),
)