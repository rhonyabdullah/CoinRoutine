package com.learn.cmm.portofolio.data

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.domain.api.CoinsRemoteDataSource
import com.learn.cmm.portofolio.data.local.PortfolioDao
import com.learn.cmm.portofolio.data.local.UserBalanceDao
import com.learn.cmm.portofolio.data.local.UserBalanceEntity
import com.learn.cmm.portofolio.domain.PortfolioCoinModel
import com.learn.cmm.portofolio.domain.PortfolioRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.domain.onError
import com.learn.cmm.core.domain.onSuccess
import com.learn.cmm.portofolio.data.mapper.toPortfolioCoinModel
import kotlinx.coroutines.flow.catch

class PortfolioRepositoryImpl(
    private val portfolioDao: PortfolioDao,
    private val userBalanceDao: UserBalanceDao,
    private val coinsRemoteDataSource: CoinsRemoteDataSource,
) : PortfolioRepository {

    override suspend fun initializeBalance() {
        val cashBalance = userBalanceDao.getCashBalance()
        if (cashBalance == null) {
            userBalanceDao.insertBalance(
                UserBalanceEntity(cashBalance = 10000.0)
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allPortfolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>> {
        return portfolioDao.getAllOwnedCoins().flatMapLatest { portfolioCoinsEntities ->
            if (portfolioCoinsEntities.isEmpty()) {
                flow {
                    emit(Result.Success(emptyList<PortfolioCoinModel>()))
                }
            } else {
                flow {
                    coinsRemoteDataSource.getListOfCoins()
                        .onError { error ->
                            emit(Result.Error(error))
                        }
                        .onSuccess { coinsDto ->
                            val portfolioCoins =
                                portfolioCoinsEntities.mapNotNull { portfolioCoinsEntity ->
                                    val coin =
                                        coinsDto.data.coins.find { it.uuid == portfolioCoinsEntity.coinId }
                                    coin?.let {
                                        portfolioCoinsEntity.toPortfolioCoinModel(it.price)
                                    }
                                }
                            emit(Result.Success(portfolioCoins))
                        }
                }
            }
        }.catch {
            emit(Result.Error(DataError.Remote.UNKNOWN))
        }
    }
}
