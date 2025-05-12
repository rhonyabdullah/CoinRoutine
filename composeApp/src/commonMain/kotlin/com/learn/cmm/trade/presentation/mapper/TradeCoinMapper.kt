package com.learn.cmm.trade.presentation.mapper

import com.learn.cmm.core.domain.coin.Coin
import com.learn.cmm.trade.presentation.common.UiTradeCoinItem

fun UiTradeCoinItem.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    iconUrl = iconUrl,
)
