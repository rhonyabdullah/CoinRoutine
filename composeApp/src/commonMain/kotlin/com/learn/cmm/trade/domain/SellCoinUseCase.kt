package com.learn.cmm.trade.domain

import com.learn.cmm.core.domain.DataError
import com.learn.cmm.core.domain.EmptyResult
import com.learn.cmm.core.domain.coin.Coin
import com.learn.cmm.portofolio.domain.PortfolioRepository
import com.learn.cmm.core.domain.Result
import kotlinx.coroutines.flow.first

class SellCoinUseCase(
    private val portfolioRepository: PortfolioRepository,
) {

    suspend fun sellCoin(
        coin: Coin,
        amountInFiat: Double,
        price: Double,
    ): EmptyResult<DataError> {
        val sellAllThreshold = 1
        when (val existingCoinResponse = portfolioRepository.getPortfolioCoin(coin.id)) {
            is Result.Success -> {
                val existingCoin = existingCoinResponse.data
                val sellAmountInUnit = amountInFiat / price

                val balance = portfolioRepository.cashBalanceFlow().first()
                if (existingCoin == null || existingCoin.ownedAmountInUnit < sellAmountInUnit) {
                    return Result.Error(DataError.Local.INSUFFICIENT_FUNDS)
                }
                val remainingAmountFiat = existingCoin.ownedAmountInFiat - amountInFiat
                val remainingAmountUnit = existingCoin.ownedAmountInUnit - sellAmountInUnit
                if (remainingAmountFiat < sellAllThreshold) {
                    portfolioRepository.removeCoinFromPortfolio(coin.id)
                } else {
                    portfolioRepository.savePortfolioCoin(
                        existingCoin.copy(
                            ownedAmountInUnit = remainingAmountUnit,
                            ownedAmountInFiat = remainingAmountFiat,
                        )
                    )
                }
                portfolioRepository.updateCashBalance(balance + amountInFiat)
                return Result.Success(Unit)
            }

            is Result.Error -> {
                return existingCoinResponse
            }
        }
    }
}
