package com.learn.cmm.portofolio.data

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.EmptyResult
import com.learn.cmm.core.domain.Result
import com.learn.cmm.core.domain.coin.Coin
import com.learn.cmm.portofolio.domain.PortfolioCoinModel
import com.learn.cmm.portofolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakePortfolioRepository : PortfolioRepository {

    private val _data = MutableStateFlow<Result<List<PortfolioCoinModel>, DataError.Remote>>(
        Result.Success(emptyList())
    )

    private val _cashBalance = MutableStateFlow(cashBalance)
    private val _portfolioValue = MutableStateFlow(portfolioValue)
    private val listOfCoins = mutableListOf<PortfolioCoinModel>()

    override suspend fun initializeBalance() {
        //no-op
    }

    override fun allPortfolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>> {
        return _data.asStateFlow()
    }

    override suspend fun getPortfolioCoin(coinId: String): Result<PortfolioCoinModel?, DataError.Remote> {
        return Result.Success(portfolioCoin)
    }

    override suspend fun savePortfolioCoin(portfolioCoin: PortfolioCoinModel): EmptyResult<DataError.Local> {
        listOfCoins.add(portfolioCoin)
        _portfolioValue.value = listOfCoins.sumOf { it.ownedAmountInFiat }
        _data.value = Result.Success(listOfCoins)
        return Result.Success(Unit)
    }

    override suspend fun removeCoinFromPortfolio(coinId: String) {
        _data.update { Result.Success(emptyList()) }
    }

    override fun calculateTotalPortfolioValue(): Flow<Result<Double, DataError.Remote>> {
        return _portfolioValue.map { Result.Success(it) }
    }

    override fun totalBalanceFlow(): Flow<Result<Double, DataError.Remote>> {
        return _cashBalance.combine(_portfolioValue) { cashBalance, portfolioValue ->
            cashBalance + portfolioValue
        }.map {
            Result.Success(it)
        }
    }

    override fun cashBalanceFlow(): Flow<Double> {
        return _cashBalance.asStateFlow()
    }

    override suspend fun updateCashBalance(newBalance: Double) {
        _cashBalance.value = newBalance
    }

    fun simulateError() {
        _data.value = Result.Error(DataError.Remote.SERVER)
    }

    companion object {
        val fakeCoin = Coin(
            id = "fakeId",
            name = "Fake Coin",
            symbol = "$$$",
            iconUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
        )
        val portfolioCoin = PortfolioCoinModel(
            coin = fakeCoin,
            performancePercent = 90.0,
            averagePurchasePrice = 30.0,
            ownedAmountInUnit = 3000.0,
            ownedAmountInFiat = 2000.0
        )
        val cashBalance = 10000.0
        val portfolioValue = 0.0
    }
}
