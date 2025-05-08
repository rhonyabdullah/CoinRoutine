package com.learn.cmm.coins.domain.model

import com.learn.cmm.core.domain.coin.Coin

data class CoinModel(
    val coin: Coin,
    val price: Double,
    val change: Double
)
