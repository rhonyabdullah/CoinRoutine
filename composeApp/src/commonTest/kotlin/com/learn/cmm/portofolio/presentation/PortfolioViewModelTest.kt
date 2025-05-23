package com.learn.cmm.portofolio.presentation

import app.cash.turbine.test
import com.learn.cmm.core.domain.DataError
import com.learn.cmm.portofolio.data.FakePortfolioRepository
import com.learn.cmm.utils.formatFiat
import com.learn.cmm.utils.toUiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PortfolioViewModelTest {

    private lateinit var viewModel: PortfolioViewModel
    private lateinit var portfolioRepository: FakePortfolioRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        portfolioRepository = FakePortfolioRepository()
        viewModel = PortfolioViewModel(
            portfolioRepository = portfolioRepository,
            dispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `state and portfolio coins are properly combined`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.coins.isEmpty())

            val portFolioCoin = FakePortfolioRepository.portfolioCoin
            portfolioRepository.savePortfolioCoin(portFolioCoin)

            awaitItem()
            val updatedState = awaitItem()
            assertTrue(updatedState.coins.isNotEmpty())
            assertFalse(updatedState.isLoading)
            assertEquals(
                FakePortfolioRepository.portfolioCoin.coin.id,
                updatedState.coins.first().id
            )
        }
    }

    @Test
    fun `portfolio value updates when a coin is added`() = runTest {
        viewModel.state.test {
            val initiateState = awaitItem()
            assertEquals(initiateState.portfolioValue, formatFiat(10000.0))

            val portfolioCoin = FakePortfolioRepository.portfolioCoin.copy(
                ownedAmountInUnit = 50.0,
                ownedAmountInFiat = 1000.0
            )

            portfolioRepository.savePortfolioCoin(portfolioCoin)
            val updatedState = awaitItem()
            assertEquals(formatFiat(11000.0), updatedState.portfolioValue)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loading state and error message update on failure`() = runTest {
        portfolioRepository.simulateError()

        viewModel.state.test {
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals(DataError.Remote.SERVER.toUiText(), errorState.error)
        }
    }

}
