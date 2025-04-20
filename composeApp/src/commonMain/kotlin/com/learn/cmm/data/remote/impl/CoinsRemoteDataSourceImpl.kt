package com.learn.cmm.data.remote.impl

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.data.remote.dto.CoinDetailsResponseDto
import com.learn.cmm.data.remote.dto.CoinPriceHistoryResponseDto
import com.learn.cmm.data.remote.dto.CoinsResponseDto
import com.learn.cmm.domain.api.CoinsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.network.safeCall

private const val BASE_URL = "https://api.coinranking.com/v2"

class CoinsRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CoinsRemoteDataSource {

    override suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coins")
        }
    }

    override suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId/history")
        }
    }

    override suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId")
        }
    }
}
