package com.learn.cmm.coins.domain

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.domain.map
import com.learn.cmm.coins.data.mapper.toCoinModel
import com.learn.cmm.coins.domain.api.CoinsRemoteDataSource
import com.learn.cmm.coins.domain.model.CoinModel

class GetCoinDetailUseCase(
    private val repository: CoinsRemoteDataSource
) {
    suspend fun execute(id: String): Result<CoinModel, DataError.Remote> {
        return repository.getCoinById(id).map { dto ->
            dto.data.coin.toCoinModel()
        }
    }
}
