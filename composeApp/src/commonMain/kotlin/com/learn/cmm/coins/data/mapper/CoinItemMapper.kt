package com.learn.cmm.coins.data.mapper

import com.learn.cmm.core.domain.coin.Coin
import com.learn.cmm.coins.data.remote.dto.CoinItemDto
import com.learn.cmm.coins.data.remote.dto.CoinPriceDto
import com.learn.cmm.coins.domain.model.CoinModel
import com.learn.cmm.coins.domain.model.PriceModel

fun CoinItemDto.toCoinModel() = CoinModel(
    coin = Coin(
        id = uuid,
        name = name,
        symbol = symbol,
        iconUrl = iconUrl,
    ),
    price = price,
    change = change
)

fun CoinPriceDto.toPriceModel() = PriceModel(
    price = price ?: 0.0,
    timestamp = timestamp
)
