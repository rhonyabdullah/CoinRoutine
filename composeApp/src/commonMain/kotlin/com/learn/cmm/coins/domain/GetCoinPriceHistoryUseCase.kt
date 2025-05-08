package com.learn.cmm.coins.domain

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.domain.map
import com.learn.cmm.coins.data.mapper.toPriceModel
import com.learn.cmm.coins.domain.api.CoinsRemoteDataSource
import com.learn.cmm.coins.domain.model.PriceModel

class GetCoinPriceHistoryUseCase(
    private val repository: CoinsRemoteDataSource
) {
    suspend fun execute(id: String): Result<List<PriceModel>, DataError.Remote> {
        return repository.getPriceHistory(coinId = id).map { dto ->
            dto.data.history.map { it.toPriceModel() }
        }
    }
}
