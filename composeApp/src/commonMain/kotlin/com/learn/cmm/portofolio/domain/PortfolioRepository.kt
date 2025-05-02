package com.learn.cmm.portofolio.domain

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {

    suspend fun initializeBalance()
    fun allPortfolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>>
}
