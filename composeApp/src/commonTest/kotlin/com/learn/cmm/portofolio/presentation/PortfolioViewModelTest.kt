package com.learn.cmm.portofolio.presentation

import com.learn.cmm.portofolio.data.FakePortfolioRepository
import com.learn.cmm.portofolio.domain.PortfolioRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {

    private lateinit var viewModel: PortfolioViewModel
    private lateinit var portfolioRepository: PortfolioRepository

    @BeforeTest
    fun setup() {
        portfolioRepository = FakePortfolioRepository()
        viewModel = PortfolioViewModel(
            portfolioRepository = portfolioRepository,
            dispatcher = UnconfinedTestDispatcher()
        )
    }
}
