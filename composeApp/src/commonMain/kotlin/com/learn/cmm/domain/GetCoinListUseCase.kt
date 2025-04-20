package com.learn.cmm.domain

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.domain.map
import com.learn.cmm.data.mapper.toCoinModel
import com.learn.cmm.domain.api.CoinsRemoteDataSource
import com.learn.cmm.domain.model.CoinModel

class GetCoinListUseCase(
    private val repository: CoinsRemoteDataSource
) {
    suspend fun execute(): Result<List<CoinModel>, DataError.Remote> {
        return repository.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
    }
}
