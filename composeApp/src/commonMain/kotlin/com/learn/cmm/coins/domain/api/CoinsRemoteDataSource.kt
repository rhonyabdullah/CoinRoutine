package com.learn.cmm.coins.domain.api

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.coins.data.remote.dto.CoinDetailsResponseDto
import com.learn.cmm.coins.data.remote.dto.CoinPriceHistoryResponseDto
import com.learn.cmm.coins.data.remote.dto.CoinsResponseDto
import com.learn.cmm.core.domain.Result

interface CoinsRemoteDataSource {

    suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote>

    suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote>

    suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote>
}
